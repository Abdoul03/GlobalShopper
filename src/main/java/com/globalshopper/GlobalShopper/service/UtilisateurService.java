package com.globalshopper.GlobalShopper.service;

import com.globalshopper.GlobalShopper.dto.response.UtilisateurResponseDTO;
import com.globalshopper.GlobalShopper.entity.Utilisateur;
import com.globalshopper.GlobalShopper.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;


    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<Utilisateur> getAllUser(){
        List<Utilisateur> user = utilisateurRepository.findAll();
        return user;
    }
}
