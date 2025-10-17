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
@RequestMapping("/admin")
public class AdminContrroller {
    public AdminContrroller(AdminService adminService) {
        this.adminService = adminService;
    }

    private final AdminService adminService;

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
}
