package com.globalshopper.GlobalShopper.entity;


import com.globalshopper.GlobalShopper.entity.enums.UniteProduit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
public class Produit {
    public Produit(long id, String nom, String description, int prix, String urlPhoto, int moq, int stock, UniteProduit unite, Fournisseur fournisseur, Categorie categorie, List<CommandeGroupee> commandeGroupees) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.urlPhoto = urlPhoto;
        this.moq = moq;
        this.stock = stock;
        this.unite = unite;
        this.fournisseur = fournisseur;
        this.categorie = categorie;
        this.commandeGroupees = commandeGroupees;
    }

    public Produit(){

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nom;
    private String description;
    private int prix;
    private String urlPhoto;
    private int moq;
    private int stock;
    @Enumerated(EnumType.STRING)
    private UniteProduit unite;

    @ManyToOne
    @JoinColumn(name = "id_fournisseur")
    private Fournisseur fournisseur;

    @ManyToOne
    @JoinColumn(name = "id_categorie")
    private Categorie categorie;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<CommandeGroupee> commandeGroupees;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public int getMoq() {
        return moq;
    }

    public void setMoq(int moq) {
        this.moq = moq;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public UniteProduit getUnite() {
        return unite;
    }

    public void setUnite(UniteProduit unite) {
        this.unite = unite;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<CommandeGroupee> getCommandeGroupees() {
        return commandeGroupees;
    }

    public void setCommandeGroupees(List<CommandeGroupee> commandeGroupees) {
        this.commandeGroupees = commandeGroupees;
    }
}