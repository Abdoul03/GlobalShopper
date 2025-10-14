package com.globalshopper.GlobalShopper.service;

import com.globalshopper.GlobalShopper.controller.GenericCrudService;
import com.globalshopper.GlobalShopper.dto.request.UtilisateurRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.UtilisateurResponseDTO;
import com.globalshopper.GlobalShopper.entity.Utilisateur;
import com.globalshopper.GlobalShopper.repository.CrudMapper;
import com.globalshopper.GlobalShopper.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService  extends GenericCrudService<Utilisateur, Long,UtilisateurRequestDTO, UtilisateurResponseDTO> {
    public UtilisateurService(UtilisateurRepository repository, CrudMapper<Utilisateur,UtilisateurRequestDTO,UtilisateurResponseDTO> mapper) {
        super(repository,mapper);
    }
}
