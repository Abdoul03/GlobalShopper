package com.globalshopper.GlobalShopper.repository;

import com.globalshopper.GlobalShopper.entity.Fournisseur;
import com.globalshopper.GlobalShopper.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    Optional<Produit> findByFournisseur (Fournisseur fournisseur);
}
