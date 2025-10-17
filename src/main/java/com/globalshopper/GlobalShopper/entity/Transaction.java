package com.globalshopper.GlobalShopper.entity;


import com.globalshopper.GlobalShopper.entity.enums.MethodeDePayement;
import com.globalshopper.GlobalShopper.entity.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
public class Transaction {

    public Transaction(long id, int montant, TransactionType transactionType, MethodeDePayement methodeDePayement, LocalDate date, Participation participation, Wallet wallet, Fournisseur fournisseur) {
        this.id = id;
        this.montant = montant;
        this.transactionType = transactionType;
        this.methodeDePayement = methodeDePayement;
        this.date = date;
        this.participation = participation;
        this.wallet = wallet;
        this.fournisseur = fournisseur;
    }
    public Transaction(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int montant;
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
    @JoinColumn(name = "fournisser_id")
    private Fournisseur fournisseur;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
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

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }
}
