package com.globalshopper.GlobalShopper.dto.mapper;

import com.globalshopper.GlobalShopper.dto.request.CommandeGroupeeRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.CommandeGroupeeResponseDTO;
import com.globalshopper.GlobalShopper.entity.CommandeGroupee;
import com.globalshopper.GlobalShopper.entity.Produit;

import java.util.Collections;
import java.util.stream.Collectors;


public class CommandeGroupeeMapper {

    public static CommandeGroupee toEntity(CommandeGroupeeRequestDTO dto, Produit produit) {
        if (dto == null) return null;

        CommandeGroupee commande = new CommandeGroupee();
        commande.setDeadline(dto.deadline());
        commande.setDateDebut(dto.dateDebut());
        commande.setProduit(produit);

        return commande;
    }

    public static CommandeGroupeeResponseDTO toResponse(CommandeGroupee entity) {
        if (entity == null) return null;

        return new CommandeGroupeeResponseDTO(
                entity.getId(),
                entity.getMontant(),
                entity.getStatus(),
                entity.getQuantiteRequis(),
                entity.getQuaniteActuelle(),
                entity.getDeadline(),
                entity.getDateDebut(),
                ProduitMapper.toResponse(entity.getProduit()),
                entity.getParticipations() != null ?
                        entity.getParticipations().stream()
                                .map(ParticipationMapper::toResponse)
                                .collect(Collectors.toList())
                        : Collections.emptyList()
        );
    }

}
