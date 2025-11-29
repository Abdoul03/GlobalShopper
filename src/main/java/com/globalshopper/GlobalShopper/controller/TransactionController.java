package com.globalshopper.GlobalShopper.controller;

import com.globalshopper.GlobalShopper.dto.response.TransactionResponseDTO;
import com.globalshopper.GlobalShopper.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController (TransactionService transactionService) {
        this.transactionService  = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransaction () {
        return ResponseEntity.ok(transactionService.getAllTransaction());
    }

    @GetMapping("/commercant")
    public ResponseEntity<List<TransactionResponseDTO>> getCommercantTransaction () {
        return ResponseEntity.ok(transactionService.getCommercantTransaction());
    }


}

