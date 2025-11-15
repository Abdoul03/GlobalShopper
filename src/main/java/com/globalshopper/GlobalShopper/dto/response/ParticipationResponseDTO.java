package com.globalshopper.GlobalShopper.dto.response;

import com.globalshopper.GlobalShopper.entity.CommandeGroupee;
import com.globalshopper.GlobalShopper.entity.Commercant;
import com.globalshopper.GlobalShopper.entity.Transaction;

import java.time.LocalDate;

public record ParticipationResponseDTO(
        int id,
        long commandeId,
        CommercantResponseDTO commercantResponseDTO,
        LocalDate data,
        int quantite,
        double montant,
        Transaction transaction
) {
}
