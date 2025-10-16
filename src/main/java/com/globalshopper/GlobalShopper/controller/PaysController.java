package com.globalshopper.GlobalShopper.controller;

import com.globalshopper.GlobalShopper.entity.Pays;
import com.globalshopper.GlobalShopper.service.PaysService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("pays")
public class PaysController {
    private final PaysService paysService;

    @PostMapping
    public ResponseEntity<Pays> CreatePay(@RequestBody Pays pays){
        return ResponseEntity.status(HttpStatus.CREATED).body(paysService.addCountry(pays));
    }

    @GetMapping
    public ResponseEntity<List<Pays>> getAllCountry(){
        return ResponseEntity.ok(paysService.getAllCountry());
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<Pays> getCountry(@PathVariable Long countryId){
        return ResponseEntity.ok(paysService.getACountry(countryId));
    }

    @PutMapping("/{countryId}")
    public ResponseEntity<Pays> updateCountry(@PathVariable Long countryId, @RequestBody Pays pays){
        return ResponseEntity.ok(paysService.update(countryId, pays));
    }

    @DeleteMapping("/{countryId}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long countryId){
        paysService.deletePays(countryId);
        return ResponseEntity.noContent().build();
    }
}
