package com.globalshopper.GlobalShopper.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Caracteristique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String valeur;

    @ManyToOne
    @JsonBackReference("produit-caracteristique")
    @JoinColumn(name = "produit_id")
    private Produit produit;

    public Caracteristique(Long id, String nom, String valeur, Produit produit) {
        this.id = id;
        this.nom = nom;
        this.valeur = valeur;
        this.produit = produit;
    }

    public Caracteristique(){

    }

    //Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
}
