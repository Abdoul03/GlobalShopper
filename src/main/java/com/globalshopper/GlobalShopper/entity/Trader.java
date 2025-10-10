package com.globalshopper.GlobalShopper.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
public class Trader extends Utilisateur {

    @ManyToMany(mappedBy = "trader" , cascade = CascadeType.ALL)
    private List<Produit> produits;
}
