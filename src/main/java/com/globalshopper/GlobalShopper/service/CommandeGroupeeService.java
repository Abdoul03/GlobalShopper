package com.globalshopper.GlobalShopper.service;

import com.globalshopper.GlobalShopper.dto.mapper.CommandeGroupeeMapper;
import com.globalshopper.GlobalShopper.dto.request.CommandeGroupeeRequestDTO;
import com.globalshopper.GlobalShopper.dto.request.CommercantRequestDTO;
import com.globalshopper.GlobalShopper.dto.request.ParticipationRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.CommandeGroupeeResponseDTO;
import com.globalshopper.GlobalShopper.entity.*;
import com.globalshopper.GlobalShopper.entity.enums.OrderStatus;
import com.globalshopper.GlobalShopper.entity.enums.ParticipationStatus;
import com.globalshopper.GlobalShopper.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CommandeGroupeeService {

    private final CommandeGroupeeRepository commandeGroupeeRepository;
    private final CommercantRepository commercantRepository;
    private final ParticipationRepository participationRepository;
    private final ProduitRepository produitRepository;
    private final PayementService payementService;
    private final FournisseurRepository fournisseurRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;

    public CommandeGroupeeService(CommandeGroupeeRepository commandeGroupeeRepository, CommercantRepository commercantRepository,
                                  ParticipationRepository participationRepository, ProduitRepository produitRepository,
                                  PayementService payementService, FournisseurRepository fournisseurRepository,
                                  SimpMessagingTemplate messagingTemplate,NotificationRepository notificationRepository
    )
    {
        this.commandeGroupeeRepository = commandeGroupeeRepository;
        this.commercantRepository = commercantRepository;
        this.participationRepository = participationRepository;
        this.produitRepository = produitRepository;
        this.payementService = payementService;
        this.fournisseurRepository = fournisseurRepository;
        this.messagingTemplate = messagingTemplate;
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public CommandeGroupeeResponseDTO createUneCommandeGroupee(long produitId, ParticipationRequestDTO participationDTO, LocalDate deadline) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long conmercantId = Long.valueOf(authentication.getPrincipal().toString());

        if (authentication.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_COMMERCANT"))) {
            throw new RuntimeException("Seules les commercants peuvent creer des commandes groupées");
        }

        Commercant commercant = commercantRepository.findById(conmercantId).orElseThrow(()-> new EntityNotFoundException("commercant introuvable"));
        Produit produit = produitRepository.findById(produitId).orElseThrow(()-> new EntityNotFoundException("Prouduit introuvable"));

        Optional<CommandeGroupee> commandeExistanteOpt = commandeGroupeeRepository.findByProduitAndStatus(produit, OrderStatus.ENCOURS);

        // 1. VÉRIFICATION D'EXISTENCE
        if (commandeExistanteOpt.isPresent()) {
            throw new RuntimeException("Une commande groupée est déjà EN COURS pour ce produit. Veuillez la rejoindre ou attendre qu'elle soit terminée.");
        }

        // 2. VÉRIFICATION MOQ
        CommandeGroupee commandeGroupee = new CommandeGroupee();

        // 3. VÉRIFICATION QUANTITÉ DE PARTICIPATION
        if (participationDTO.quantite() > produit.getMoq()) {
            throw new RuntimeException("La quantité de votre participation initiale (" + participationDTO.quantite() + ") ne peut pas dépasser la quantité requise du groupe (" + produit.getMoq() + ").");
        }

        // --- 1. CRÉATION ET PERSISTANCE PRÉALABLE DE COMMANDEGROUPÉE ---
        commandeGroupee.setQuantiteRequis(produit.getMoq());
        commandeGroupee.setDateDebut(LocalDate.now());
        commandeGroupee.setProduit(produit);
        commandeGroupee.setCommercant(commercant); // Commerçant initiateur
        commandeGroupee.setQuaniteActuelle(0);
        commandeGroupee.setStatus(OrderStatus.ENCOURS);
        commandeGroupee.setDeadline(deadline);
        commandeGroupee.setMontant(0.0);

        // **SAUVEGARDE**
        commandeGroupee = commandeGroupeeRepository.save(commandeGroupee);

        // --- 2. CRÉATION DE LA PARTICIPATION ---
        double montant = participationDTO.quantite() * produit.getPrix();

        Participation participation = new Participation();
        participation.setData(LocalDate.now());
        participation.setQuantite(participationDTO.quantite());
        participation.setCommercant(commercant);
        participation.setMontant(montant);
        participation.setCommandeGroupee(commandeGroupee); // LIEN vers l'objet PERSISTANT

        // --- 3. PAIEMENT ET SAUVEGARDE DE L'ENFANT ---
        // Le service de paiement va créer/sauvegarder la Transaction, et potentiellement la Participation.
        payementService.effectuerPayement(participation);
        // Si la Participation n'est pas sauvée par le service de paiement ou par Cascade, le sauver ici
        participationRepository.save(participation);

        // --- 4. MISE À JOUR FINALE DE COMMANDEGROUPÉE ---
        commandeGroupee.setQuaniteActuelle(participation.getQuantite());
        commandeGroupee.setMontant(montant);

        // Mise à jour de la collection si non gérée par `save(participation)`
        if (commandeGroupee.getParticipations() == null) {
            commandeGroupee.setParticipations(new ArrayList<>());
        }
        commandeGroupee.getParticipations().add(participation);

        commandeGroupeeRepository.save(commandeGroupee); // Sauvegarde finale des mises à jour

        Notification notification = new Notification();

        notification.setTitre("Creation de commande Groupée");
        notification.setMessage("Votre commande groupée du produit : " + produit.getNom() + " a été crée avec succes.");
//        notification.setCommercant(commercant.getId());

        notificationRepository.save(notification);

        messagingTemplate.convertAndSend("/topic/orders", notification);

        return CommandeGroupeeMapper.toResponse(commandeGroupee);
    }

    public CommandeGroupeeResponseDTO rejoindreUneCommandeGroupee(long produitId, ParticipationRequestDTO participationDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long conmercantId = Long.valueOf(authentication.getPrincipal().toString());

        if (authentication.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_COMMERCANT"))) {
            throw new RuntimeException("Seules les commercants peuvent creer des commandes groupées");
        }

        Commercant commercant = commercantRepository.findById(conmercantId).orElseThrow(()-> new EntityNotFoundException("commercant introuvable"));
        Produit produit = produitRepository.findById(produitId).orElseThrow(()-> new EntityNotFoundException("Prouduit introuvable"));
        // ... (Vérifications d'authentification et récupération Commercant/Produit) ...

        Optional<CommandeGroupee> commandeExistanteOpt = commandeGroupeeRepository.findByProduitAndStatus(produit, OrderStatus.ENCOURS);

        // 1. VÉRIFICATION D'EXISTENCE
        if (commandeExistanteOpt.isEmpty()) {
            throw new EntityNotFoundException("Aucune commande groupée EN COURS n'existe pour ce produit (" + produit.getNom() + ").");
            // Ou
            // return createUneCommandeGroupee(produitId, participationDTO, LocalDate.now().plusDays(7)); // Utiliser une deadline par défaut
        }

        CommandeGroupee commandeGroupee = commandeExistanteOpt.get();

        // 2. VÉRIFICATION STATUT/DEADLINE
        if (commandeGroupee.getStatus() != OrderStatus.ENCOURS || (commandeGroupee.getDeadline() != null && commandeGroupee.getDeadline().isBefore(LocalDate.now()))) {
            throw new RuntimeException("La commande groupée n'est plus en cours ou la deadline est dépassée.");
        }

        // 3. VÉRIFICATION PARTICIPATION EXISTANTE
        boolean dejaParticipant = commandeGroupee.getParticipations().stream()
                .anyMatch(p -> p.getCommercant().getId() == commercant.getId());

        if (dejaParticipant) {
            throw new RuntimeException("Vous participez déjà à cette commande groupée.");
        }

        // 4. VÉRIFICATION QUANTITÉ
        int quantiteDemandee = participationDTO.quantite();
        int nouvelleQuantite = commandeGroupee.getQuaniteActuelle() + quantiteDemandee;

        if (nouvelleQuantite > commandeGroupee.getQuantiteRequis()) {
            throw new RuntimeException("Impossible de rejoindre : la quantité demandée dépasse le reste requis. Reste max : " + (commandeGroupee.getQuantiteRequis() - commandeGroupee.getQuaniteActuelle()));
        }


        double montant = quantiteDemandee * produit.getPrix();

        Participation participation = new Participation();
        participation.setData(LocalDate.now());
        participation.setQuantite(quantiteDemandee);
        participation.setCommercant(commercant);
        participation.setMontant(montant);
        participation.setCommandeGroupee(commandeGroupee);

        payementService.effectuerPayement(participation);

        // 5. MISE À JOUR ET SAUVEGARDE
        commandeGroupee.getParticipations().add(participation);
        commandeGroupee.setQuaniteActuelle(nouvelleQuantite);
        commandeGroupee.setMontant(commandeGroupee.getMontant() + montant);

        if (nouvelleQuantite >= commandeGroupee.getQuantiteRequis()) {
            commandeGroupee.setStatus(OrderStatus.TERMINER);
            payementService.payementFournisseur(commandeGroupee.getId());
        }

        commandeGroupeeRepository.save(commandeGroupee);
        participationRepository.save(participation);

        Notification notification = new Notification();

        notification.setTitre("Adhesion a la commande Groupée");
        notification.setMessage("Vous avez rejoin la commande groupée du produit : " + produit.getNom() + " avec succes.");
//        notification.setCommercant(commercant);

        notificationRepository.save(notification);

        messagingTemplate.convertAndSend("/topic/orders", notification);

        return CommandeGroupeeMapper.toResponse(commandeGroupee);
    }

    @Transactional
    public CommandeGroupeeResponseDTO retirerParticipation(int commandeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long commercantId = Long.valueOf(authentication.getPrincipal().toString());

        CommandeGroupee commande = commandeGroupeeRepository.findById(commandeId)
                .orElseThrow(() -> new EntityNotFoundException("Commande groupée introuvable"));

        if (commande.getStatus() != OrderStatus.ENCOURS) {
            throw new RuntimeException("Impossible d'annuler une participation sur une commande déjà complète ou terminée.");
        }

        // Trouver la participation du commerçant
        Participation participation = commande.getParticipations().stream().filter(p -> p.getCommercant().getId() == commercantId)
                .findFirst().orElseThrow(() -> new RuntimeException("Vous ne participez pas à cette commande."));

        // Créer une transaction de remboursement
        payementService.rembourserParticipation(participation);

        participation.setStatus(ParticipationStatus.ANNULEE);
        commande.getParticipations().remove(participation);

        // Mettre à jour la commande
        commande.setQuaniteActuelle(commande.getQuaniteActuelle() - participation.getQuantite());
        commande.setMontant(commande.getMontant() - participation.getMontant());


        // Si plus de participants
        if (commande.getParticipations().isEmpty()) {
            commande.setStatus(OrderStatus.ANNULER);
        }

        var c =  commandeGroupeeRepository.save(commande);

        return CommandeGroupeeMapper.toResponse(c);
    }

    public List<CommandeGroupeeResponseDTO> getAllCommandeGrouper(){
        List<CommandeGroupee>  commandeGroupee = commandeGroupeeRepository.findAll();
        return commandeGroupee.stream().map(CommandeGroupeeMapper :: toResponse).toList();
    }

    public CommandeGroupeeResponseDTO getAOrderGroupe(int commandeId){
        CommandeGroupee commandeGroupee = commandeGroupeeRepository.findById(commandeId).orElseThrow(
                ()-> new EntityNotFoundException("Commande groupée introuvable")
        );
        return CommandeGroupeeMapper.toResponse(commandeGroupee);
    }

    public boolean deleteOrderGroup(int commandeId){
        CommandeGroupee commandeGroupee = commandeGroupeeRepository.findById(commandeId).orElseThrow(
                ()-> new EntityNotFoundException("Commande groupée introuvable")
        );
        return true;
    }

    public List<CommandeGroupeeResponseDTO> allOrderCreateByTrader(long commercantId){
        List<CommandeGroupee> commande = commandeGroupeeRepository.findAllByCommercantId(
                commercantId).orElseThrow(()-> new EntityNotFoundException("Commande goupée not fund")
        );
        return  commande.stream().map(CommandeGroupeeMapper::toResponse).toList();
    }

    public List<CommandeGroupeeResponseDTO> allOrderOfTrader(long commercantId){
        List<CommandeGroupee> allcommande = commandeGroupeeRepository.findAll();

        return  allcommande.stream().filter(
                commandeGroupee -> commandeGroupee.getParticipations()
                        .stream().anyMatch(p -> p.getCommercant().getId() == commercantId))
                .map(CommandeGroupeeMapper::toResponse).toList();
    }

    public CommandeGroupeeResponseDTO orderCreateByTrader(long commercantId){
        CommandeGroupee commandeGroupee = commandeGroupeeRepository.findByCommercantId(commercantId).orElseThrow(
                ()-> new EntityNotFoundException("Commande introuvable"));

        return CommandeGroupeeMapper.toResponse(commandeGroupee);
    }

    public List<CommandeGroupeeResponseDTO> allSupplierOrder (long fournisseurId) {
        Fournisseur fournisseur = fournisseurRepository.findById(fournisseurId).orElseThrow(()-> new EntityNotFoundException("Fournisseur introuvable"));
        List<Produit> produits = produitRepository.findByFournisseurId(fournisseur.getId());
        //List<CommandeGroupee> commandes = new ArrayList<>();
        Set<CommandeGroupee> commandes = new HashSet<>();
        for (Produit produit : produits){
            if (produit.getCommandeGroupees() != null) {
                commandes.addAll(produit.getCommandeGroupees());
            }
        }
        return commandes.stream().map(CommandeGroupeeMapper :: toResponse).toList();
    }

    public CommandeGroupeeResponseDTO modifierLeDeadLineDuCommande(long commercantId, CommandeGroupeeRequestDTO commande){
        CommandeGroupee commandeGroupee = commandeGroupeeRepository.findByCommercantId(commercantId).orElseThrow(
                ()-> new EntityNotFoundException("Commande introuvable"));

        commandeGroupee.setDeadline(commande.deadline());
        commandeGroupee.setStatus(OrderStatus.ENCOURS);

        var commandeModifier = commandeGroupeeRepository.save(commandeGroupee);

        return CommandeGroupeeMapper.toResponse(commandeModifier);

    }

    @Transactional
    public void annulerCommandeApresDeadline(long commercantId){
        CommandeGroupee commandeGroupee = commandeGroupeeRepository.findByCommercantId(commercantId).orElseThrow(
                ()-> new EntityNotFoundException("Commande introuvable")
        );

        List<Participation> tousLesParticipations = commandeGroupee.getParticipations();

        for (Participation participation1 : tousLesParticipations){
            try{
                payementService.rembourserParticipation(participation1);
                commandeGroupee.getParticipations().remove(participation1);
            }catch (Exception e){
                throw new RuntimeException("Échec critique lors de l'annulation (remboursement échoué) : " + e.getMessage());
            }
        }

        commandeGroupee.setStatus(OrderStatus.ANNULER);

        commandeGroupeeRepository.save(commandeGroupee);
    }


    @Scheduled(cron = "0 35 1 * * *") // Chaque jour à minuit
    @Transactional // Nécessaire pour que toutes les opérations (remboursement/sauvegarde) soient atomiques
    public void annulerCommandesDateLimiteDepassee() {
        // Définir la date d'aujourd'hui pour la comparaison
        LocalDate today = LocalDate.now();

        // 1. Trouver toutes les commandes qui sont ENCOURS ET dont le deadline est passé
        List<CommandeGroupee> commandesAAnnuler = commandeGroupeeRepository
                .findByStatusAndDeadlineBefore(OrderStatus.ENCOURS, today);

        // Si nous utilisons la logique de prolongation (EN_ATTENTE_PROLONGATION) :
        // List<CommandeGroupee> commandesAAnnuler = commandeGroupeeRepository
        //     .findByStatusInAndDeadlineBefore(
        //          Arrays.asList(OrderStatus.ENCOURS, OrderStatus.EN_ATTENTE_PROLONGATION),
        //          today
        //     );


        if (commandesAAnnuler.isEmpty()) {
            System.out.println("Aucune commande à annuler automatiquement.");
            return;
        }

        System.out.println("Début de l'annulation de " + commandesAAnnuler.size() + " commandes.");

        // 2. Traiter chaque commande
        for (CommandeGroupee commande : commandesAAnnuler) {
            try {
                // Annulation et remboursement des participants
                processerAnnulationEtRemboursement(commande);

            } catch (Exception e) {
                // En cas d'échec critique (ex: échec du remboursement pour l'une des participations)
                System.err.println("Échec de l'annulation et du remboursement pour la commande ID: "
                        + commande.getId() + ". Erreur: " + e.getMessage());
                // La transaction sera annulée (rollback) pour cette commande si @Transactional est bien configuré.
            }
        }
        System.out.println("Processus d'annulation automatique terminé.");
    }

    private void processerAnnulationEtRemboursement(CommandeGroupee commande) {
        //Rembourser tous les participants
        List<Participation> participations = commande.getParticipations();
        for (Participation participation : participations) {
            payementService.rembourserParticipation(participation);
        }

        commande.setStatus(OrderStatus.ANNULER); // Ou OrderStatus.ANNULER_AUTO
        commandeGroupeeRepository.save(commande);

        System.out.println("Commande ID: " + commande.getId() + " annulée et remboursée avec succès.");
    }

}
