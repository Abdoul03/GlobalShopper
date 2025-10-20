package com.globalshopper.GlobalShopper.dto.mapper;

import com.globalshopper.GlobalShopper.dto.request.ParticipationRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.ParticipationResponseDTO;
import com.globalshopper.GlobalShopper.entity.CommandeGroupee;
import com.globalshopper.GlobalShopper.entity.Commercant;
import com.globalshopper.GlobalShopper.entity.Participation;
import org.springframework.stereotype.Component;

@Component
public class ParticipationMapper {

    private final CommercantMapp commercantMapper;
    private final CommandeGroupeeMapper commandeGroupeeMapper;

    public ParticipationMapper(CommercantMapp commercantMapper, CommandeGroupeeMapper commandeGroupeeMapper) {
        this.commercantMapper = commercantMapper;
        this.commandeGroupeeMapper = commandeGroupeeMapper;
    }

    public Participation toEntity(ParticipationRequestDTO dto, Commercant commercant, CommandeGroupee commandeGroupee) {
        if (dto == null) return null;
        Participation participation = new Participation();
        participation.setCommercant(commercant);
        participation.setCommandeGroupee(commandeGroupee);
        participation.setData(dto.data());
        participation.setQuantite(dto.quantite());
        participation.setMontant(dto.montant());
        participation.setTransaction(dto.transaction());
        return participation;
    }

    // Surcharge simple (pour mappage dans CommandeGroupeeMapper)
    public Participation toEntity(ParticipationRequestDTO dto) {
        if (dto == null) return null;
        Participation participation = new Participation();
        participation.setData(dto.data());
        participation.setQuantite(dto.quantite());
        participation.setMontant(dto.montant());
        participation.setTransaction(dto.transaction());
        return participation;
    }

    public ParticipationResponseDTO toResponse(Participation entity) {
        if (entity == null) return null;
        return new ParticipationResponseDTO(
                entity.getId(),
                commandeGroupeeMapper.toResponse(entity.getCommandeGroupee()), commercantMapper.toResponse(entity.getCommercant()),
                entity.getData(),
                entity.getQuantite(),
                entity.getMontant(),
                entity.getTransaction()
        );
    }
}
