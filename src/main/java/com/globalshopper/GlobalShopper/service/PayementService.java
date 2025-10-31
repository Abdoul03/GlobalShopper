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

        Wallet wallet = walletRepository.findByNumero("71267813").orElseThrow(() -> new IllegalStateException("Le wallet global est introuvable"));;

        Transaction transaction = new Transaction();
        transaction.setMontant(participation.getMontant());
        transaction.setTransactionType(TransactionType.RESERVATION);
        transaction.setDate(LocalDate.now());
        transaction.setParticipation(participation);
        transaction.setMethodeDePayement(MethodeDePayement.ORANGE_MONEY);

        // Simulation du paiement
        boolean paiementReussi = simulerPaiement(participation.getMontant());

        if (paiementReussi) {
            transaction.setStatut(Statut.EFFECTUER);
            transaction.setWallet(wallet);
            transactionRepository.save(transaction);
        } else {
            transaction.setStatut(Statut.ANNULER);
            transaction.setMethodeDePayement(MethodeDePayement.ORANGE_MONEY);
        }

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        // Si paiement réussi, créer ou mettre à jour le wallet
        if (paiementReussi) {
            wallet.setMontant(transaction.getMontant());
            wallet.setStatut(Statut.EFFECTUER);
            wallet.setMiseAjour(LocalDate.now());
            wallet.setTransactions(listTransaction);

            walletRepository.save(wallet);
        } else {
            transaction.setStatut(Statut.ANNULER);
            transactionRepository.save(transaction);
            throw new RuntimeException("Le paiement a échoué pour un montant de : " + participation.getMontant());
        }

        return transaction;
    }


    @Transactional
    public Transaction rembourserParticipation(Participation participation) {
        try {
            Transaction remboursement = new Transaction();
            remboursement.setMontant(participation.getMontant());
            remboursement.setTransactionType(TransactionType.REMBOURSEMENT);
            remboursement.setMethodeDePayement(MethodeDePayement.ORANGE_MONEY);
            remboursement.setParticipation(participation);
            remboursement.setDate(LocalDate.now());

            transactionRepository.save(remboursement);

            // Met à jour le wallet (argent retourné au commerçant)
//            Wallet wallet = walletRepository.findByCommercant(participation.getCommercant())
//                    .orElse(new Wallet());
            Wallet wallet = walletRepository.findById(remboursement.getWallet().getId()).orElseThrow(()-> new EntityNotFoundException("wallet introuvable"));
            wallet.setMontant(wallet.getMontant() - remboursement.getMontant());
            wallet.setStatut(Statut.EFFECTUER);
            wallet.setMiseAjour(LocalDate.now());

            walletRepository.save(wallet);

            return remboursement;

        } catch (Exception e) {
            throw new RuntimeException("Échec du remboursement : " + e.getMessage());
        }
    }

    @Transactional
    public Transaction payementFournisseur(int commandId){
        try {
            CommandeGroupee commandeGroupee = commandeGroupeeRepository.findById(commandId).orElseThrow(
                    ()-> new EntityNotFoundException("Aucune commande groupé trouver"));

            double montant = commandeGroupee.getMontant() * 0.5;
            Fournisseur fournisseur = commandeGroupee.getProduit().getFournisseur();
            CompteFourisseur compteFourisseur = fournisseur.getCompteFourisseur();


            Transaction payementFournisseur = new Transaction();

            payementFournisseur.setDate(LocalDate.now());
            payementFournisseur.setMethodeDePayement(MethodeDePayement.ORANGE_MONEY);
            payementFournisseur.setTransactionType(TransactionType.PAYEMENT_FOURNISSEUR);
            payementFournisseur.setMontant(montant);
            payementFournisseur.setCompteFourisseur(compteFourisseur);


            transactionRepository.save(payementFournisseur);

            Wallet wallet = walletRepository.findById(payementFournisseur.getWallet().getId()).orElseThrow(()-> new EntityNotFoundException("wallet introuvable"));
            wallet.setMontant(wallet.getMontant() - payementFournisseur.getMontant());
            wallet.setStatut(Statut.EFFECTUER);
            wallet.setMiseAjour(LocalDate.now());

            walletRepository.save(wallet);

            compteFourisseur.setMontant(montant);
            compteFourisseur.setFournisseur(fournisseur);
            compteFourisseur.setMethodeDePayement(MethodeDePayement.ORANGE_MONEY);

            compteFournisseurRepository.save(compteFourisseur);

            return payementFournisseur;

        }catch (Exception e){
            throw new RuntimeException("Échec de payement du fournisseur : " + e.getMessage());
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
