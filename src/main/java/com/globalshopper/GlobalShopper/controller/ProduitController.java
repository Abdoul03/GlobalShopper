package com.globalshopper.GlobalShopper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globalshopper.GlobalShopper.dto.request.ProduitRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.ProduitResponseDTO;
import com.globalshopper.GlobalShopper.service.ProduitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("produits")
public class ProduitController {
    private ProduitService produitService;
    private ObjectMapper objectMapper;

    public ProduitController(ProduitService produitService, ObjectMapper objectMapper) {
        this.produitService = produitService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ProduitResponseDTO> ajouterProduit(
            @RequestPart("produit") String produitRequest, // ton JSON
            @RequestPart(value = "images", required = true) MultipartFile[] images     // tes fichiers
    ) throws IOException {
        ProduitRequestDTO dto = objectMapper.readValue(produitRequest, ProduitRequestDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(produitService.ajouterProduit(dto,images));
    }

}
