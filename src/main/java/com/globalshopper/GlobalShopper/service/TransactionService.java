package com.globalshopper.GlobalShopper.service;

import com.globalshopper.GlobalShopper.dto.mapper.TransactionMapper;
import com.globalshopper.GlobalShopper.dto.response.CommercantResponseDTO;
import com.globalshopper.GlobalShopper.dto.response.TransactionResponseDTO;
import com.globalshopper.GlobalShopper.entity.Commercant;
import com.globalshopper.GlobalShopper.entity.Transaction;
import com.globalshopper.GlobalShopper.repository.CommercantRepository;
import com.globalshopper.GlobalShopper.repository.FournisseurRepository;
import com.globalshopper.GlobalShopper.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final FournisseurRepository fournisseurRepository;

    public TransactionService (TransactionRepository transactionRepository, FournisseurRepository fournisseurRepository){
        this.transactionRepository = transactionRepository;
        this.fournisseurRepository = fournisseurRepository;
    }

    public List<TransactionResponseDTO> getAllTransaction(){
        List<Transaction> transaction = transactionRepository.findAll();
        return transaction.stream().map(TransactionMapper :: toResponse).toList();
    }

    public List<TransactionResponseDTO> getCommercantTransaction(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long commercantId = Long.valueOf(authentication.getPrincipal().toString());

        List<Transaction> transaction = transactionRepository.findByParticipationCommercantId(commercantId);;

        return transaction.stream()
                .map(TransactionMapper :: toResponse)
                .toList();
    }


}
