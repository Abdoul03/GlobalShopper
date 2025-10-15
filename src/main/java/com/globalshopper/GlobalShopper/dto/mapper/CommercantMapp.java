package com.globalshopper.GlobalShopper.dto.mapper;

import com.globalshopper.GlobalShopper.dto.request.CommercantRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.CommercantResponseDTO;
import com.globalshopper.GlobalShopper.entity.Commercant;

public class CommercantMapp {
    public static Commercant toEntity(CommercantRequestDTO dtoCommercant, Commercant commercant){
        commercant.setNom(dtoCommercant.nom());
        commercant.setPrenom(dtoCommercant.prenom());
        commercant.setUsername(dtoCommercant.username());
        commercant.setEmail(dtoCommercant.email());
        commercant.setMotDePasse(dtoCommercant.motDePasse());
        commercant.setTelephone(dtoCommercant.telephone());
        commercant.setActif(dtoCommercant.actif());
        commercant.setRole(dtoCommercant.role());
        return commercant;
    }

    public static CommercantResponseDTO toResponse(Commercant commercant){
        if(commercant == null) return null;

        CommercantResponseDTO commercantResponse = new CommercantResponseDTO(
                commercant.getId(),
                commercant.getNom(),
                commercant.getPrenom(),
                commercant.getUsername(),
                commercant.getTelephone(),
                commercant.getEmail(),
                commercant.isActif(),
                commercant.getPhotoUrl(),
                commercant.getPays(),
                commercant.getRole(),
                commercant.getParticipation()
        );
        return commercantResponse;
    }
}
