package com.globalshopper.GlobalShopper.dto.request;

import com.globalshopper.GlobalShopper.entity.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;

public record CommandeGroupeeRequestDTO(
        int montant,
        OrderStatus status,
        int quantiteRequis,
        int quaniteActuelle,
        LocalDate deadline,
        LocalDate dateDebut,
        long produitId,
        List<ParticipationRequestDTO> participation
) {
}
