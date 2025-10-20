package com.globalshopper.GlobalShopper.dto.mapper;

import com.globalshopper.GlobalShopper.dto.request.CommandeGroupeeRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.CommandeGroupeeResponseDTO;
import com.globalshopper.GlobalShopper.entity.CommandeGroupee;
import com.globalshopper.GlobalShopper.entity.Produit;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class CommandeGroupeeMapper {
    private static ProduitMapper produitMapper;
    private static ParticipationMapper participationMapper;

    public CommandeGroupeeMapper() {
    }

    public static CommandeGroupee toEntity(CommandeGroupeeRequestDTO dto, Produit produit) {
        if (dto == null) return null;

        CommandeGroupee commande = new CommandeGroupee();
        commande.setMontant(dto.montant());
        commande.setStatus(dto.status());
        commande.setQuantiteRequis(dto.quantiteRequis());
        commande.setQuaniteActuelle(dto.quaniteActuelle());
        commande.setDeadline(dto.deadline());
        commande.setDateDebut(dto.dateDebut());
        commande.setProduit(produit);

        if (dto.participation() != null) {
            commande.setParticipations(
                    dto.participation().stream()
                            .map(participationMapper::toEntity)
                            .collect(Collectors.toList())
            );
        }

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
                produitMapper.toResponse(entity.getProduit()),
                entity.getParticipations() != null ?
                        entity.getParticipations().stream()
                                .map(participationMapper::toResponse)
                                .collect(Collectors.toList())
                        : Collections.emptyList()
        );
    }

}
