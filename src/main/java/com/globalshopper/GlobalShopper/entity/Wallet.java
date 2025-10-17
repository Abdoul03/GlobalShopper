package com.globalshopper.GlobalShopper.entity;


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
    public Wallet(long id, int montant, LocalDate miseAjour, Statut statut, List<Transaction> transactions) {
        this.id = id;
        this.montant = montant;
        this.miseAjour = miseAjour;
        this.statut = statut;
        this.transactions = transactions;
    }
    public Wallet (){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int montant;
    private LocalDate miseAjour;
    @Enumerated(EnumType.STRING)
    private Statut statut;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

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
}
