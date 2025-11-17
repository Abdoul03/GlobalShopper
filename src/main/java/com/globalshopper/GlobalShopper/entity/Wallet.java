package com.globalshopper.GlobalShopper.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.globalshopper.GlobalShopper.entity.enums.Statut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Wallet {
    public Wallet(long id, double montant, LocalDate miseAjour, Statut statut, List<Transaction> transactions, String numero) {
        this.id = id;
        this.montant = montant;
        this.miseAjour = miseAjour;
        this.statut = statut;
        this.transactions = transactions;
        this.numero = numero;
    }
    public Wallet (){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double montant;
    private LocalDate miseAjour;
    @Enumerated(EnumType.STRING)
    private Statut statut;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
    @JsonManagedReference("wallet-transactions")
    private List<Transaction> transactions;

    private String numero;

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

    public LocalDate getMiseAjour() {
        return miseAjour;
    }

    public void setMiseAjour(LocalDate miseAjour) {
        this.miseAjour = miseAjour;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getNumero() {return numero;}

    public void setNumero(String numero) {this.numero = numero;}
}
