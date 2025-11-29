package com.globalshopper.GlobalShopper.dto.mapper;

import com.globalshopper.GlobalShopper.dto.response.TransactionResponseDTO;
import com.globalshopper.GlobalShopper.entity.Transaction;

public class TransactionMapper {

    public static TransactionResponseDTO toResponse(Transaction transaction ){
        if(transaction == null) return null;

        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getMontant(),
                transaction.getTransactionType(),
                transaction.getMethodeDePayement(),
                transaction.getDate(),
                transaction.getParticipation()
        );
    }
}
