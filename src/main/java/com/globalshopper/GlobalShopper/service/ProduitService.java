package com.globalshopper.GlobalShopper.service;

import com.globalshopper.GlobalShopper.repository.ProduitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProduitService {
    private final ProduitRepository repository;


}
