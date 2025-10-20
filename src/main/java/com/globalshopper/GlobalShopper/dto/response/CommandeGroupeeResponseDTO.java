package com.globalshopper.GlobalShopper.dto.response;

import com.globalshopper.GlobalShopper.entity.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;

public record CommandeGroupeeResponseDTO(
        long id,
        int montant,
        OrderStatus status,
        int quantiteRequis,
        int quanitrActuelle,
        LocalDate deadline,
        LocalDate dateDebut,
        ProduitResponseDTO produit,
        List<ParticipationResponseDTO> participation
) {
}
