package com.globalshopper.GlobalShopper.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
public class Participation {
    public Participation(int id, Commercant commercant, CommandeGroupee commandeGroupee, LocalDate data, int quantite, double montant, Transaction transaction) {
        Id = id;
        this.commercant = commercant;
        this.commandeGroupee = commandeGroupee;
        this.data = data;
        this.quantite = quantite;
        this.montant = montant;
        this.transaction = transaction;
    }
    public Participation(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference("commercant-participation")
    @JoinColumn(name = "Commercant_id")
    private Commercant commercant;

    @ManyToOne
    @JsonBackReference("commande-participation")
    @JoinColumn(name = "commandeGroupe_Id")
    private CommandeGroupee commandeGroupee;

    private LocalDate data;
    private int quantite;
    private double montant;

    @OneToOne(mappedBy = "participation")
    @JsonManagedReference("participation-transaction")
    private Transaction transaction;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Commercant getCommercant() {
        return commercant;
    }

    public void setCommercant(Commercant commercant) {
        this.commercant = commercant;
    }

    public CommandeGroupee getCommandeGroupee() {
        return commandeGroupee;
    }

    public void setCommandeGroupee(CommandeGroupee commandeGroupee) {
        this.commandeGroupee = commandeGroupee;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
