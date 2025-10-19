package com.globalshopper.GlobalShopper.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
public class Fournisseur extends Utilisateur {

    public Fournisseur(List<Produit> produit, List<Transaction> transactions) {
        this.produit = produit;
        this.transactions = transactions;
    }

    public Fournisseur(){

    }

    @OneToMany(mappedBy = "fournisseur", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Produit> produit;

    @OneToMany(mappedBy = "fournisseur", cascade = CascadeType.ALL)
    private List<Transaction> transactions;


    public List<Produit> getProduit() {
        return produit;
    }

    public void setProduit(List<Produit> produit) {
        this.produit = produit;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
