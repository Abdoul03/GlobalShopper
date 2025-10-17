package com.globalshopper.GlobalShopper.service;

import com.globalshopper.GlobalShopper.repository.ProduitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class ProduitService {
    private final ProduitRepository repository;

    public ProduitService(ProduitRepository repository) {
        this.repository = repository;
    }
}
