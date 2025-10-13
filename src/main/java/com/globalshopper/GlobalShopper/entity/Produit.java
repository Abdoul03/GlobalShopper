package com.globalshopper.GlobalShopper.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nom;
    private String description;
    private int prix;
    private String urlPhoto;
    private int moq;
    private int stock;

    @ManyToOne
    @JoinColumn(name = "id_fournisseur")
    private Fournisseur fournisseur;

    @ManyToMany
    @JoinTable(name = "produit_commercant", joinColumns= @JoinColumn(name = "id_produit"), inverseJoinColumns = @JoinColumn(name = "id_commercant"))
    private List<Commercant> commercant;

    @ManyToOne
    @JoinColumn(name = "id_categorie")
    private Categorie categorie;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<CommandeGroupee> commandeGroupees;
}