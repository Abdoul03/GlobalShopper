package com.globalshopper.GlobalShopper.controller;

import com.globalshopper.GlobalShopper.dto.request.CommercantRequestDTO;
import com.globalshopper.GlobalShopper.dto.request.UtilisateurRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.CommercantResponseDTO;
import com.globalshopper.GlobalShopper.dto.response.FournisseurResponseDTO;
import com.globalshopper.GlobalShopper.dto.response.UtilisateurResponseDTO;
import com.globalshopper.GlobalShopper.entity.Commercant;
import com.globalshopper.GlobalShopper.service.CommercantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/commercant")
public class CommercantController {
    private final CommercantService commercantService;

    @GetMapping
    public ResponseEntity<List<CommercantResponseDTO>> getAllCommercant () {
        return ResponseEntity.ok(commercantService.getAllCommercant());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommercantResponseDTO> getCommercant(@PathVariable Long id){
        return ResponseEntity.ok(commercantService.getByCommercantId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommercantResponseDTO> updateCommercant(@PathVariable Long id, @RequestBody CommercantRequestDTO commercant){
        return ResponseEntity.ok(commercantService.updateCommercant(id, commercant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCommercant(@PathVariable long id){
        return ResponseEntity.ok(commercantService.deleteCommercant(id));
    }
}
