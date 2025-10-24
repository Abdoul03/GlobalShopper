package com.globalshopper.GlobalShopper.controller;

import com.globalshopper.GlobalShopper.entity.Utilisateur;
import com.globalshopper.GlobalShopper.service.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("utilisateur")
public class UtilisateurController {
    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAllUsers(){
        return ResponseEntity.ok(utilisateurService.getAllUser());
    }
}
