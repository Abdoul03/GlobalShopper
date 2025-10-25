package com.globalshopper.GlobalShopper.controller;

import com.globalshopper.GlobalShopper.dto.request.CategorieRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.CategorieResponseDTO;
import com.globalshopper.GlobalShopper.entity.Categorie;
import com.globalshopper.GlobalShopper.service.CategorieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categorie")
public class CategorieController {

    private final CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @PostMapping
    public ResponseEntity<CategorieResponseDTO> createCaterogi(@RequestBody CategorieRequestDTO categorie){
        return ResponseEntity.status(HttpStatus.CREATED).body(categorieService.createCategorie(categorie));
    }

    @GetMapping
    public ResponseEntity<List<CategorieResponseDTO>> getAllCategorie(){
        return ResponseEntity.ok(categorieService.getAllCategorie());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategorieResponseDTO> trouverUneCategorie(@PathVariable Long id){
        return ResponseEntity.ok(categorieService.getACategorie(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategorieResponseDTO> updateCategorie(@PathVariable Long id ,@RequestBody CategorieRequestDTO  categorie){
        return ResponseEntity.ok(categorieService.updateCategorie(id,categorie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id){
        categorieService.deleteCategorie(id);
        return ResponseEntity.noContent().build();
    }
}
