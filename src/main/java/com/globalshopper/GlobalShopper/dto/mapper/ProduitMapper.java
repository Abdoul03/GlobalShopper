package com.globalshopper.GlobalShopper.dto.mapper;

import com.globalshopper.GlobalShopper.dto.request.ProduitRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.FournisseurResponseDTO;
import com.globalshopper.GlobalShopper.dto.response.ProduitResponseDTO;
import com.globalshopper.GlobalShopper.entity.Caracteristique;
import com.globalshopper.GlobalShopper.entity.Categorie;
import com.globalshopper.GlobalShopper.entity.Produit;

import java.util.List;
import java.util.stream.Collectors;

public class ProduitMapper {
    public static Produit toEntity (ProduitRequestDTO produitRequestDTO, Categorie categorie){
        Produit produit = new Produit();
        produit.setNom(produitRequestDTO.nom());
        produit.setDescription(produitRequestDTO.description());
        produit.setPrix(produitRequestDTO.prix());
        produit.setMoq(produitRequestDTO.moq());
        produit.setCategorie(categorie);
        produit.setUnite(produitRequestDTO.unite());

        // Conversion des caractéristiques DTO -> entités
        List<Caracteristique> caracteristiques = produitRequestDTO.caracteristiques() != null
                ? produitRequestDTO.caracteristiques().stream()
                .map(c -> {
                    Caracteristique caract = new Caracteristique();
                    caract.setNom(c.nom());
                    caract.setValeur(c.valeur());
                    caract.setProduit(produit); // important pour la relation bidirectionnelle
                    return caract;
                })
                .collect(Collectors.toList())
                : List.of();

        produit.setCaracteristiques(caracteristiques);
        return produit;
    }
    public static ProduitResponseDTO toResponse (Produit produit){
        if (produit == null) return null;

        return new ProduitResponseDTO(
                produit.getId(),
                produit.getNom(),
                produit.getDescription(),
                produit.getPrix(),
                produit.getMoq(),
                produit.getStock(),
                produit.getUnite(),
                produit.getCaracteristiques(),
                produit.getFournisseur(),
                produit.getCategorie(),
                produit.getCommandeGroupees()
        );
    }
}
