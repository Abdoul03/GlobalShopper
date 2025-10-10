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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String nom;
    private String description;
    private int prix;
    private String urlPhoto;
    private int moq;
    private int stock;

    @ManyToOne
    @JoinColumn(name = "id_supplier")
    private Supplier supplier;

    @ManyToMany
    @JoinTable(name = "produit_commercant", joinColumns= @JoinColumn(name = "id_produit"), inverseJoinColumns = @JoinColumn(name = "id_trader"))
    private List<Trader> trader;

    @ManyToOne
    @JoinColumn(name = "id_categorie")
    private Categorie categorie;
}