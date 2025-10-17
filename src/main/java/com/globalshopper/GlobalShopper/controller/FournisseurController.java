package com.globalshopper.GlobalShopper.controller;

import com.globalshopper.GlobalShopper.dto.request.FournisseurRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.FournisseurResponseDTO;
import com.globalshopper.GlobalShopper.service.FournisseurService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fournisseur")
public class FournisseurController {
    private FournisseurService fournisseurService;

    public FournisseurController(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    @GetMapping
    public ResponseEntity<List<FournisseurResponseDTO>> getAllFournisseur(){
        return ResponseEntity.ok(fournisseurService.getAllFournisseur());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FournisseurResponseDTO> getFournisserur(@PathVariable Long id){
        return ResponseEntity.ok(fournisseurService.getFournisseur(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FournisseurResponseDTO> update(@PathVariable Long id, @RequestBody FournisseurRequestDTO fournisseur){
        return ResponseEntity.ok(fournisseurService.updateFournisseur(id, fournisseur));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        fournisseurService.deleteFournisseur(id);
        return ResponseEntity.noContent().build();
    }
}
