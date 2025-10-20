package com.globalshopper.GlobalShopper.dto.request;

import com.globalshopper.GlobalShopper.entity.Transaction;
import java.time.LocalDate;

public record ParticipationRequestDTO (
        CommercantRequestDTO commercant,
        CommandeGroupeeRequestDTO commandeGroupee,
        LocalDate data,
        int quantite,
        int montant,
        Transaction transaction,
        long CommandeId
){
}
