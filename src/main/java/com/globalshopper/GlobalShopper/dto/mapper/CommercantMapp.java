package com.globalshopper.GlobalShopper.dto.mapper;

import com.globalshopper.GlobalShopper.dto.request.CommercantRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.CommercantResponseDTO;
import com.globalshopper.GlobalShopper.dto.response.MediaResponseDTO;
import com.globalshopper.GlobalShopper.dto.response.ParticipationResponseDTO;
import com.globalshopper.GlobalShopper.dto.response.PaysResponseDTO;
import com.globalshopper.GlobalShopper.entity.Commercant;

import java.util.List;
import java.util.stream.Collectors;

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
        PaysResponseDTO paysDTO = null;
        if(commercant.getPays() != null){
            paysDTO = new PaysResponseDTO(
                    commercant.getPays().getId(),
                    commercant.getPays().getNom()
            );
        }

//        List<ParticipationResponseDTO> participation = commercant.getParticipation().stream()
//                .map(p-> new ParticipationResponseDTO(
//                        p.getId(),
//                        CommandeGroupeeMapper.toResponse(p.getCommandeGroupee()),
//                        CommercantMapp.toResponse(p.getCommercant()),
//                        p.getData(),
//                        p.getQuantite(),
//                        p.getMontant(),
//                        p.getTransaction())
//                ).collect(Collectors.toList());

        CommercantResponseDTO commercantResponse = new CommercantResponseDTO(
                commercant.getId(),
                commercant.getNom(),
                commercant.getPrenom(),
                commercant.getUsername(),
                commercant.getTelephone(),
                commercant.getEmail(),
                commercant.isActif(),
                commercant.getPhotoUrl(),
                paysDTO,
                commercant.getRole()
              //  participation
        );
        return commercantResponse;
    }
}
