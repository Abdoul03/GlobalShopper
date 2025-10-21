package com.globalshopper.GlobalShopper.entity;


import com.globalshopper.GlobalShopper.entity.enums.MethodeDePayement;
import com.globalshopper.GlobalShopper.entity.enums.Statut;
import com.globalshopper.GlobalShopper.entity.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
public class Transaction {

    public Transaction(long id, double montant,
                       TransactionType transactionType,
                       MethodeDePayement methodeDePayement, LocalDate date, Participation participation,
                       Wallet wallet, CompteFourisseur compteFourisseur, Statut statut) {
        this.id = id;
        this.montant = montant;
        this.transactionType = transactionType;
        this.methodeDePayement = methodeDePayement;
        this.date = date;
        this.participation = participation;
        this.wallet = wallet;
        this.compteFourisseur = compteFourisseur;
        this.statut = statut;
    }
    public Transaction(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double montant;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    private MethodeDePayement methodeDePayement;

    private LocalDate date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "participation_id")
    private Participation participation;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "compteFournisseur_id")
    private CompteFourisseur compteFourisseur;

    private Statut statut;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public MethodeDePayement getMethodeDePayement() {
        return methodeDePayement;
    }

    public void setMethodeDePayement(MethodeDePayement methodeDePayement) {
        this.methodeDePayement = methodeDePayement;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Participation getParticipation() {
        return participation;
    }

    public void setParticipation(Participation participation) {
        this.participation = participation;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public CompteFourisseur getCompteFourisseur() {
        return compteFourisseur;
    }

    public void setCompteFourisseur(CompteFourisseur compteFourisseur) {
        this.compteFourisseur = compteFourisseur;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }
}
