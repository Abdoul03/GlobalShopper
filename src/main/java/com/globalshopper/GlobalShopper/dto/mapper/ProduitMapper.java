package com.globalshopper.GlobalShopper.dto.mapper;

import com.globalshopper.GlobalShopper.dto.request.ProduitRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.*;
import com.globalshopper.GlobalShopper.entity.Caracteristique;
import com.globalshopper.GlobalShopper.entity.Categorie;
import com.globalshopper.GlobalShopper.entity.Produit;
import org.springframework.stereotype.Component;

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
                .collect(Collectors.toList()) : List.of();

        produit.setCaracteristiques(caracteristiques);
        return produit;
    }
    public static ProduitResponseDTO toResponse (Produit produit){
        if (produit == null) return null;

        List<CaracteristiqueResponseDTO> caracteristiquesDTO = produit.getCaracteristiques().stream()
                .map(c -> new CaracteristiqueResponseDTO(c.getId(), c.getNom(), c.getValeur()))
                .collect(Collectors.toList());
        List<MediaResponseDTO>  mediaDTO = produit.getMedia().stream()
                .map(m -> new MediaResponseDTO(m.getId(), m.getFileName(), m.getFileType(), m.getFilePath(), m.getWebPath())).collect(Collectors.toList());

        FournisseurResponseDTO fournisseurDTO = getResponseDTO(produit);

        CategorieResponseDTO categorieDTO = null;
        if(produit.getCategorie() != null){
            categorieDTO = new CategorieResponseDTO(
                    produit.getCategorie().getId(),
                    produit.getCategorie().getNom()
            );
        }

        return new ProduitResponseDTO(
                produit.getId(),
                produit.getNom(),
                produit.getDescription(),
                produit.getPrix(),
                produit.getMoq(),
                produit.getStock(),
                produit.getUnite(),
                categorieDTO,
                caracteristiquesDTO ,
                mediaDTO,
                fournisseurDTO,
                produit.getCommandeGroupees()
        );
    }

    private static FournisseurResponseDTO getResponseDTO(Produit produit) {
        FournisseurResponseDTO fournisseurDTO = null;

        PaysResponseDTO paysDTO = null;
        if(produit.getFournisseur().getPays() != null){
            paysDTO = new PaysResponseDTO(
                    produit.getFournisseur().getPays().getId(),
                    produit.getFournisseur().getPays().getNom()
            );
        }

        if(produit.getFournisseur() != null){
            fournisseurDTO = new  FournisseurResponseDTO(
                    produit.getFournisseur().getId(),
                    produit.getFournisseur().getNom(),
                    produit.getFournisseur().getPrenom(),
                    produit.getFournisseur().getUsername(),
                    produit.getFournisseur().getTelephone(),
                    produit.getFournisseur().getEmail(),
                    produit.getFournisseur().isActif(),
                    produit.getFournisseur().getPhotoUrl(),
                    paysDTO,
                    produit.getFournisseur().getRole()
            );
        }
        return fournisseurDTO;
    }
}
