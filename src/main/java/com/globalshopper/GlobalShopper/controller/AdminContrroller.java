package com.globalshopper.GlobalShopper.controller;

import com.globalshopper.GlobalShopper.dto.request.UtilisateurRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.UtilisateurResponseDTO;
import com.globalshopper.GlobalShopper.entity.Admin;
import com.globalshopper.GlobalShopper.entity.Pays;
import com.globalshopper.GlobalShopper.service.AdminService;
import com.globalshopper.GlobalShopper.service.PaysService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminContrroller extends GenericCrudController<Admin,Long, UtilisateurRequestDTO, UtilisateurResponseDTO>{
    private final PaysService paysService;

    public AdminContrroller(AdminService adminService,PaysService paysService) {
        super(adminService);
        this.paysService = paysService;
    }

    @PostMapping("/pays")
    public ResponseEntity<Pays> CreatePay(@RequestBody Pays pays){
        return ResponseEntity.status(HttpStatus.CREATED).body(paysService.addCountry(pays));
    }

    @GetMapping("/pays")
    public ResponseEntity<List<Pays>> getAllCountry(){
        return ResponseEntity.ok(paysService.getAllCountry());
    }

    @GetMapping("pays/{countryId}")
    public ResponseEntity<Pays> getCountry(@PathVariable Long countryId){
        return ResponseEntity.ok(paysService.getACountry(countryId));
    }

    @PutMapping("pays/{countryId}")
    public ResponseEntity<Pays> updateCountry(@PathVariable Long countryId, @RequestBody Pays pays){
        return ResponseEntity.ok(paysService.update(countryId, pays));
    }

    @DeleteMapping("pays/{countryId}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long countryId){
        paysService.deletePays(countryId);
        return ResponseEntity.noContent().build();
    }
}
