package com.globalshopper.GlobalShopper.service;

import com.globalshopper.GlobalShopper.dto.mapper.CommandeGroupeeMapper;
import com.globalshopper.GlobalShopper.dto.request.ParticipationRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.CommandeGroupeeResponseDTO;
import com.globalshopper.GlobalShopper.entity.*;
import com.globalshopper.GlobalShopper.entity.enums.OrderStatus;
import com.globalshopper.GlobalShopper.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    public CommandeGroupeeService(CommandeGroupeeRepository commandeGroupeeRepository, CommercantRepository commercantRepository,
                                  ParticipationRepository participationRepository, ProduitRepository produitRepository,
                                  PayementService payementService, FournisseurRepository fournisseurRepository)
    {
        this.commandeGroupeeRepository = commandeGroupeeRepository;
        this.commercantRepository = commercantRepository;
        this.participationRepository = participationRepository;
        this.produitRepository = produitRepository;
        this.payementService = payementService;
        this.fournisseurRepository = fournisseurRepository;
    }

    @Transactional
    public CommandeGroupeeResponseDTO createUneCommandeGroupee(long prouitId, ParticipationRequestDTO participationDTO, LocalDate deadline){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long conmercantId = Long.valueOf(authentication.getPrincipal().toString());

        if (authentication.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_COMMERCANT"))) {
            throw new RuntimeException("Seules les commercants peuvent creer des commandes groupées");
        }

        Commercant commercant = commercantRepository.findById(conmercantId).orElseThrow(()-> new EntityNotFoundException("commercant introuvable"));
        Produit produit = produitRepository.findById(prouitId).orElseThrow(()-> new EntityNotFoundException("Prouduit introuvable"));

        Optional<CommandeGroupee> commandeExistanteOpt = commandeGroupeeRepository.findByProduitAndStatus(produit, OrderStatus.ENCOURS);


        CommandeGroupee commandeGroupee;

        if (commandeExistanteOpt.isEmpty()){
            //Creer une commande groupée si le produit n'a pas de commande

            commandeGroupee = new CommandeGroupee();

            Participation participation = new Participation();

            List<Participation> listParticipant = new ArrayList<>();

            if (produit.getMoq() < commandeGroupee.getQuantiteRequis()){
                throw new RuntimeException("la quantité requise ne peux pas etre supperieur a la quantitée minimun de commande");
            }
            double montant = participationDTO.quantite() * produit.getPrix();

            participation.setData(LocalDate.now());
            participation.setQuantite(participationDTO.quantite());
            participation.setCommercant(commercant);
            participation.setMontant(montant);

            payementService.effectuerPayement(participation);

            participationRepository.save(participation);

            listParticipant.add(participation);

            commandeGroupee.setDateDebut(LocalDate.now());
            commandeGroupee.setQuantiteRequis(produit.getMoq());
            commandeGroupee.setMontant(montant);
            commandeGroupee.setProduit(produit);
            commandeGroupee.setCommercant(commercant);
            commandeGroupee.setQuaniteActuelle(participation.getQuantite());
            commandeGroupee.setStatus(OrderStatus.ENCOURS);
            commandeGroupee.setParticipations(listParticipant);
            commandeGroupee.setDeadline(deadline);

            commandeGroupeeRepository.save(commandeGroupee);

        } else {
            // === CAS 2 : Rejoindre une commande existante ===
            commandeGroupee = commandeExistanteOpt.get();

            // Vérifier si le commerçant participe déjà
            boolean dejaParticipant = commandeGroupee.getParticipations().stream()
                    .anyMatch(p -> p.getCommercant().getId() == commercant.getId());

            if (dejaParticipant) {
                throw new RuntimeException("Vous participez déjà à cette commande groupée");
            }

            int quantiteDemandee = participationDTO.quantite();
            int nouvelleQuantite = commandeGroupee.getQuaniteActuelle() + quantiteDemandee;

            if (nouvelleQuantite > commandeGroupee.getQuantiteRequis()) {
                throw new RuntimeException("Impossible de rejoindre : quantité requise déjà atteinte ou dépassée");
            }

            double montant = quantiteDemandee * produit.getPrix();

            Participation participation = new Participation();
            participation.setData(LocalDate.now());
            participation.setQuantite(quantiteDemandee);
            participation.setCommercant(commercant);
            participation.setMontant(montant);

            payementService.effectuerPayement(participation);
            participationRepository.save(participation);

            commandeGroupee.getParticipations().add(participation);
            commandeGroupee.setQuaniteActuelle(nouvelleQuantite);
            commandeGroupee.setMontant(commandeGroupee.getMontant() + montant);

            // Si la quantité requise est atteinte → cloture automatique
            if (nouvelleQuantite >= commandeGroupee.getQuantiteRequis()) {
                commandeGroupee.setStatus(OrderStatus.TERMINER);
                payementService.payementFournisseur(commandeGroupee.getId());
            }

            commandeGroupeeRepository.save(commandeGroupee);
        }

        return CommandeGroupeeMapper.toResponse(commandeGroupee);
    }


    public CommandeGroupeeResponseDTO rejoindreUneCommandeGroupee(long prouitId, ParticipationRequestDTO participationDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long conmercantId = Long.valueOf(authentication.getPrincipal().toString());

        if (authentication.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_COMMERCANT"))) {
            throw new RuntimeException("Seules les commercants peuvent creer des commandes groupées");
        }

        Commercant commercant = commercantRepository.findById(conmercantId).orElseThrow(()-> new EntityNotFoundException("commercant introuvable"));
        Produit produit = produitRepository.findById(prouitId).orElseThrow(()-> new EntityNotFoundException("Prouduit introuvable"));

        Optional<CommandeGroupee> commandeExistanteOpt = commandeGroupeeRepository.findByProduitAndStatus(produit, OrderStatus.ENCOURS);

        CommandeGroupee commandeGroupee = null;
        if (commandeExistanteOpt.isPresent()){
            // === CAS 2 : Rejoindre une commande existante ===
             commandeGroupee = commandeExistanteOpt.get();

            // Vérifier si le commerçant participe déjà
            boolean dejaParticipant = commandeGroupee.getParticipations().stream()
                    .anyMatch(p -> p.getCommercant().getId() == commercant.getId());

            if (dejaParticipant) {
                throw new RuntimeException("Vous participez déjà à cette commande groupée");
            }

            int quantiteDemandee = participationDTO.quantite();
            int nouvelleQuantite = commandeGroupee.getQuaniteActuelle() + quantiteDemandee;

            if (nouvelleQuantite > commandeGroupee.getQuantiteRequis()) {
                throw new RuntimeException("Impossible de rejoindre : quantité requise déjà atteinte ou dépassée");
            }

            double montant = quantiteDemandee * produit.getPrix();

            Participation participation = new Participation();
            participation.setData(LocalDate.now());
            participation.setQuantite(quantiteDemandee);
            participation.setCommercant(commercant);
            participation.setMontant(montant);
            participation.setCommandeGroupee(commandeGroupee);

            payementService.effectuerPayement(participation);
            participationRepository.save(participation);

            commandeGroupee.getParticipations().add(participation);
            commandeGroupee.setQuaniteActuelle(nouvelleQuantite);
            commandeGroupee.setMontant(commandeGroupee.getMontant() + montant);

            // Si la quantité requise est atteinte → cloture automatique
            if (nouvelleQuantite >= commandeGroupee.getQuantiteRequis()) {
                commandeGroupee.setStatus(OrderStatus.TERMINER);
                payementService.payementFournisseur(commandeGroupee.getId());
            }

            commandeGroupeeRepository.save(commandeGroupee);
        }else {
            createUneCommandeGroupee(prouitId, participationDTO,commandeGroupee.getDeadline());
        }
        return CommandeGroupeeMapper.toResponse(commandeGroupee);
    }

    @Transactional
    public CommandeGroupeeResponseDTO retirerParticipation(int commandeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long commercantId = Long.valueOf(authentication.getPrincipal().toString());

        Commercant commercant = commercantRepository.findById(commercantId)
                .orElseThrow(() -> new EntityNotFoundException("Commerçant introuvable"));

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

        // Mettre à jour la commande
        commande.setQuaniteActuelle(commande.getQuaniteActuelle() - participation.getQuantite());
        commande.setMontant(commande.getMontant() - participation.getMontant());
        commande.getParticipations().remove(participation);

        participationRepository.delete(participation);

        // Si plus de participants
        if (commande.getParticipations().isEmpty()) {
            commande.setStatus(OrderStatus.ANNULER);
        }

        commandeGroupeeRepository.save(commande);

        return CommandeGroupeeMapper.toResponse(commande);
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

}
