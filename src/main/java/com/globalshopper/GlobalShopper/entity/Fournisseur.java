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

    public Fournisseur(List<Produit> produit, CompteFourisseur compteFourisseur) {
        this.produit = produit;
        this.compteFourisseur  = compteFourisseur;
    }

    public Fournisseur(){

    }

    @OneToMany(mappedBy = "fournisseur", cascade = CascadeType.ALL)
    @JsonManagedReference("produit-fournisseur")
    private List<Produit> produit;

    @OneToOne
    private CompteFourisseur compteFourisseur;


    public List<Produit> getProduit() {
        return produit;
    }

    public void setProduit(List<Produit> produit) {
        this.produit = produit;
    }

    public CompteFourisseur getCompteFourisseur() {
        return compteFourisseur;
    }

    public void setCompteFourisseur(CompteFourisseur compteFourisseur) {
        this.compteFourisseur = compteFourisseur;
    }
}
