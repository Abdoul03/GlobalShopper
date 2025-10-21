package com.globalshopper.GlobalShopper.entity;

import com.globalshopper.GlobalShopper.entity.enums.MethodeDePayement;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class CompteFourisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String numero;
    private double Montant;
    private MethodeDePayement methodeDePayement;

    @OneToOne
    private Fournisseur fournisseur;

    @OneToMany(mappedBy = "compteFourisseur", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    // constructors

    public CompteFourisseur(long id, String numero, double montant, MethodeDePayement methodeDePayement, Fournisseur fournisseur, List<Transaction> transactions) {
        this.id = id;
        this.numero = numero;
        Montant = montant;
        this.methodeDePayement = methodeDePayement;
        this.fournisseur = fournisseur;
        this.transactions = transactions;
    }

    public CompteFourisseur() {
    }

    //Accesseurs

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getMontant() {
        return Montant;
    }

    public void setMontant(double montant) {
        Montant = montant;
    }

    public MethodeDePayement getMethodeDePayement() {
        return methodeDePayement;
    }

    public void setMethodeDePayement(MethodeDePayement methodeDePayement) {
        this.methodeDePayement = methodeDePayement;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
