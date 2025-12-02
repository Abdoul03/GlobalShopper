package com.globalshopper.GlobalShopper.service;


import com.globalshopper.GlobalShopper.entity.*;
import com.globalshopper.GlobalShopper.entity.enums.MethodeDePayement;
import com.globalshopper.GlobalShopper.entity.enums.Statut;
import com.globalshopper.GlobalShopper.entity.enums.TransactionType;
import com.globalshopper.GlobalShopper.repository.CommandeGroupeeRepository;
import com.globalshopper.GlobalShopper.repository.CompteFoournisseurRepository;
import com.globalshopper.GlobalShopper.repository.TransactionRepository;
import com.globalshopper.GlobalShopper.repository.WalletRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PayementService {
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final CommandeGroupeeRepository commandeGroupeeRepository;
    private final CompteFoournisseurRepository compteFournisseurRepository;

    public PayementService(TransactionRepository transactionRepository, WalletRepository walletRepository, CommandeGroupeeRepository commandeGroupeeRepository, CompteFoournisseurRepository compteFournisseurRepository) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.commandeGroupeeRepository = commandeGroupeeRepository;
        this.compteFournisseurRepository = compteFournisseurRepository;
    }

    @Transactional
    public Transaction effectuerPayement(Participation participation){

        if (participation == null) {
            throw new IllegalArgumentException("La participation ne peut pas être nulle");
        }

        // --- 1. Création de la Transaction de Réservation ---
        Transaction transaction = new Transaction();
        transaction.setMontant(participation.getMontant());
        transaction.setTransactionType(TransactionType.RESERVATION); // Réservation des fonds
        transaction.setDate(LocalDate.now());
        transaction.setParticipation(participation);

        // Pour la simulation de paiement, nous supposons que les fonds ont bien été prélevés sur le compte du commerçant.
        boolean paiementReussi = simulerPaiement(participation.getMontant());

        if (paiementReussi) {
            transaction.setStatut(Statut.EFFECTUER);
        } else {
            transaction.setStatut(Statut.ANNULER);
            // Si le paiement échoue, on lève l'exception ici
            transactionRepository.save(transaction); // Sauvegarde de la transaction annulée
            throw new RuntimeException("Le paiement de réservation a échoué pour un montant de : " + participation.getMontant());
        }

        transaction.setMethodeDePayement(MethodeDePayement.ORANGE_MONEY);
        transactionRepository.save(transaction);

        // --- 2. Mise à jour du Wallet (Compte de Séquestre du Système) ---

        // Récupérer le Wallet du système (nécessite une méthode pour le trouver, ex: findSystemWallet())
        // Je suppose ici une méthode de repository pour récupérer le Wallet unique du système.
        Wallet wallet = walletRepository.findSystemWallet()
                .orElseGet(() -> {
                    // Créer le Wallet si c'est la première transaction
                    Wallet newWallet = new Wallet();
                    newWallet.setMontant(0.0);
                    newWallet.setStatut(Statut.EFFECTUER);
                    //newWallet.setType(WalletType.SYSTEM_ESCROW); // Si vous avez un type de wallet
                    return newWallet;
                });

        // Mettre à jour le solde du Wallet de séquestre
        wallet.setMontant(wallet.getMontant() + transaction.getMontant());
        wallet.setMiseAjour(LocalDate.now());

        // Assurez-vous d'ajouter la transaction au Wallet (géré par la relation OneToMany)
        if (wallet.getTransactions() == null) {
            wallet.setTransactions(new ArrayList<>());
        }
        wallet.getTransactions().add(transaction);

        walletRepository.save(wallet);

        return transaction;
    }

    @Transactional
    public Transaction payementFournisseur(int commandeGroupeeId){

        CommandeGroupee commandeGroupee = commandeGroupeeRepository.findById(commandeGroupeeId)
                .orElseThrow(() -> new EntityNotFoundException("Commande groupée introuvable"));

        // 1. Calcul du montant à payer
        double montantTotal = commandeGroupee.getMontant();
        double montantAPayer = montantTotal * 0.50; // Payer 50% au fournisseur

        // 2. Récupérer le Wallet du système
        Wallet systemWallet = walletRepository.findSystemWallet()
                .orElseThrow(() -> new RuntimeException("Wallet du système introuvable."));

        // 3. Vérification du solde
        if (systemWallet.getMontant() < montantTotal) {
            // En théorie, cela ne devrait jamais arriver si toutes les réservations ont réussi,
            // mais c'est une bonne sécurité (on doit avoir le montant total pour pouvoir payer 50% en toute sécurité).
            throw new RuntimeException("Fonds insuffisants dans le Wallet du système pour payer le fournisseur.");
        }

        // 4. Création de la Transaction de Paiement Fournisseur
        Transaction transactionPaiement = new Transaction();
        transactionPaiement.setMontant(montantAPayer);
        transactionPaiement.setTransactionType(TransactionType.PAYEMENT_FOURNISSEUR);
        transactionPaiement.setDate(LocalDate.now());
        transactionPaiement.setStatut(Statut.EFFECTUER); // Supposons que ce paiement réussit
        // Note : Il faudrait idéalement lier cette transaction au Fournisseur, mais cette information
        // n'est pas directement dans votre modèle ici (elle pourrait être via Produit -> Fournisseur).

        transactionRepository.save(transactionPaiement);

        // 5. Mise à jour du Wallet du système (Débit)
        systemWallet.setMontant(systemWallet.getMontant() - montantAPayer);
        systemWallet.setMiseAjour(LocalDate.now());
        systemWallet.getTransactions().add(transactionPaiement);

        walletRepository.save(systemWallet);

        return transactionPaiement;
    }


    @Transactional
    public Transaction rembourserParticipation(Participation participation) {
        try {
            Transaction remboursement = participation.getTransaction();
//            remboursement.setMontant(participation.getMontant());
            remboursement.setTransactionType(TransactionType.REMBOURSEMENT);
            remboursement.setMethodeDePayement(MethodeDePayement.ORANGE_MONEY);
//            remboursement.setParticipation(participation);
            remboursement.setDate(LocalDate.now());


            transactionRepository.delete(remboursement);

            // Met à jour le wallet (argent retourné au commerçant)
//            Wallet wallet = walletRepository.findByCommercant(participation.getCommercant())
//                    .orElse(new Wallet());
            // 2. Récupérer le Wallet du système
            Wallet wallet = walletRepository.findSystemWallet()
                    .orElseThrow(() -> new RuntimeException("Wallet du système introuvable."));
            wallet.setMontant(wallet.getMontant() - remboursement.getMontant());
            wallet.setStatut(Statut.EFFECTUER);
            wallet.setMiseAjour(LocalDate.now());

            walletRepository.save(wallet);

            return remboursement;

        } catch (Exception e) {
            throw new RuntimeException("Échec du remboursement : " + e.getMessage());
        }
    }

    /**
     * Simulation aléatoire d’un paiement.
     * Par exemple, 90% de réussite.
     */
    private boolean simulerPaiement(double montant) {
        double chance = Math.random(); // entre 0.0 et 1.0
        return chance < 0.9; // 90% de réussite
    }
}