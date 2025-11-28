package com.globalshopper.GlobalShopper.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globalshopper.GlobalShopper.dto.request.ProduitRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.CommandeGroupeeResponseDTO;
import com.globalshopper.GlobalShopper.dto.response.ProduitResponseDTO;
import com.globalshopper.GlobalShopper.service.ProduitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("produits")
public class ProduitController {
    private final ProduitService produitService;
    private final ObjectMapper objectMapper;

    public ProduitController(ProduitService produitService, ObjectMapper objectMapper) {
        this.produitService = produitService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ProduitResponseDTO> ajouterProduit(
            @RequestPart("produit") String produitRequest,
            @RequestPart(value = "images", required = true) MultipartFile[] images
    ) throws IOException {
        ProduitRequestDTO dto = objectMapper.readValue(produitRequest, ProduitRequestDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(produitService.ajouterProduit(dto,images));
    }

    @GetMapping("/fournisseur")
    public ResponseEntity<List<ProduitResponseDTO>> trouverLesProduitsDuFournisseur(){
        return ResponseEntity.ok(produitService.getProduitBySupplierId());
    }

    @GetMapping("/fournisseur/{id}")
    public ResponseEntity<List<ProduitResponseDTO>> trouverLesProduitsDuFournisseurParAutre(@PathVariable Long id){
        return ResponseEntity.ok(produitService.getProduitByfournisseurId(id));
    }

    @GetMapping
    public ResponseEntity<List<ProduitResponseDTO>> getAllProduit(){
        return ResponseEntity.ok(produitService.getProduit());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduitResponseDTO> getAnProduit(@PathVariable Long id){
        return ResponseEntity.ok(produitService.getAProduict(id));
    }

    @GetMapping("/{produitId}/participation-utilisateur")
    public ResponseEntity<CommandeGroupeeResponseDTO> getCommercantParticipation(@PathVariable Long produitId){
        return ResponseEntity.ok(produitService.getParticipationCommande(produitId));
    }

    @GetMapping("/last/{id}")
    public ResponseEntity<CommandeGroupeeResponseDTO> getLastCommande(@PathVariable long id){
        return ResponseEntity.ok(produitService.getLastOrder(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProduitResponseDTO> updateProduct(@PathVariable Long id, @RequestPart("produit") String produitRequest) throws JsonProcessingException {
        ProduitRequestDTO dto = objectMapper.readValue(produitRequest, ProduitRequestDTO.class);
        return ResponseEntity.ok(produitService.updateProduct(id,dto));
    }

    @DeleteMapping("/{id}")
    public String deleteProduit (@PathVariable Long id){
        return produitService.deleteProduct(id);
    }
}
