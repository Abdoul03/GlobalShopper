package com.globalshopper.GlobalShopper.controller;

import com.globalshopper.GlobalShopper.dto.request.UtilisateurRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.UtilisateurResponseDTO;
import com.globalshopper.GlobalShopper.entity.Admin;
import com.globalshopper.GlobalShopper.entity.Pays;
import com.globalshopper.GlobalShopper.service.AdminService;
import com.globalshopper.GlobalShopper.service.PaysService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminContrroller {

    private final AdminService adminService;
    private final PaysService paysService;

    @PostMapping
    public ResponseEntity<Admin> addAdministrateur(@RequestBody Admin admin){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.creatAdmin(admin));
    }
    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmin(){
        return ResponseEntity.ok(adminService.getAllAdmin());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getadm(@PathVariable Long id){
        return ResponseEntity.ok(adminService.getAAdmin(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id){
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
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
