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
public class Supplier extends Utilisateur {

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Produit> produit;

}
