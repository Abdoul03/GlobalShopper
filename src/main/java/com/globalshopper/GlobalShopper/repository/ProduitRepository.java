package com.globalshopper.GlobalShopper.repository;

import com.globalshopper.GlobalShopper.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
}
