package com.globalshopper.GlobalShopper.dto.response;

import com.globalshopper.GlobalShopper.entity.Participation;
import com.globalshopper.GlobalShopper.entity.enums.MethodeDePayement;
import com.globalshopper.GlobalShopper.entity.enums.TransactionType;

import java.time.LocalDate;

public record TransactionResponseDTO(
        long id,
        double montant,
        TransactionType transactionType,
        MethodeDePayement methodeDePayement,
        LocalDate date,
        Participation participation
) {
}
