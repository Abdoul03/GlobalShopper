package com.globalshopper.GlobalShopper.dto.mapper;

import com.globalshopper.GlobalShopper.dto.request.ParticipationRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.ParticipationResponseDTO;
import com.globalshopper.GlobalShopper.entity.CommandeGroupee;
import com.globalshopper.GlobalShopper.entity.Commercant;
import com.globalshopper.GlobalShopper.entity.Participation;

public class ParticipationMapper {

    public static Participation toEntity(ParticipationRequestDTO dto, Commercant commercant, CommandeGroupee commandeGroupee) {
        if (dto == null) return null;
        Participation participation = new Participation();
        participation.setCommercant(commercant);
        participation.setCommandeGroupee(commandeGroupee);
        participation.setQuantite(dto.quantite());
        return participation;
    }

    // Surcharge simple (pour mappage dans CommandeGroupeeMapper)
    public static Participation toEntity(ParticipationRequestDTO dto) {
        if (dto == null) return null;
        Participation participation = new Participation();
        participation.setQuantite(dto.quantite());
        return participation;
    }

    public static ParticipationResponseDTO toResponse(Participation entity) {
        if (entity == null) return null;
        return new ParticipationResponseDTO(
                entity.getId(),
                CommandeGroupeeMapper.toResponse(entity.getCommandeGroupee()), CommercantMapp.toResponse(entity.getCommercant()),
                entity.getData(),
                entity.getQuantite(),
                entity.getMontant(),
                entity.getTransaction()
        );
    }
}
