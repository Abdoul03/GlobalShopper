package com.globalshopper.GlobalShopper.repository;

import com.globalshopper.GlobalShopper.dto.response.ProduitResponseDTO;
import com.globalshopper.GlobalShopper.entity.Fournisseur;
import com.globalshopper.GlobalShopper.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    List<Produit> findByFournisseurId (long fournisseurId);
}
