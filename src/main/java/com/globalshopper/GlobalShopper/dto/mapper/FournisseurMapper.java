package com.globalshopper.GlobalShopper.dto.mapper;

import com.globalshopper.GlobalShopper.dto.request.CommercantRequestDTO;
import com.globalshopper.GlobalShopper.dto.request.FournisseurRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.CommercantResponseDTO;
import com.globalshopper.GlobalShopper.dto.response.FournisseurResponseDTO;
import com.globalshopper.GlobalShopper.entity.Commercant;
import com.globalshopper.GlobalShopper.entity.Fournisseur;

public class FournisseurMapper {

    public static Fournisseur toEntity(FournisseurRequestDTO fournisseurDto, Fournisseur fournisseur){
        fournisseur.setNom(fournisseurDto.nom());
        fournisseur.setPrenom(fournisseurDto.prenom());
        fournisseur.setUsername(fournisseurDto.username());
        fournisseur.setEmail(fournisseurDto.email());
        fournisseur.setMotDePasse(fournisseurDto.motDePasse());
        fournisseur.setTelephone(fournisseurDto.telephone());
        fournisseur.setActif(fournisseurDto.actif());
        fournisseur.setRole(fournisseurDto.role());
        return fournisseur;
    }

    public static FournisseurResponseDTO toResponse(Fournisseur fournisseur){
        if(fournisseur == null) return null;

        return new FournisseurResponseDTO(
                fournisseur.getId(),
                fournisseur.getNom(),
                fournisseur.getPrenom(),
                fournisseur.getUsername(),
                fournisseur.getTelephone(),
                fournisseur.getEmail(),
                fournisseur.isActif(),
                fournisseur.getPhotoUrl(),
                fournisseur.getPays(),
                fournisseur.getRole()
        );
    }
}
