package com.globalshopper.GlobalShopper.service;

import com.globalshopper.GlobalShopper.controller.GenericCrudService;
import com.globalshopper.GlobalShopper.dto.request.UtilisateurRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.UtilisateurResponseDTO;
import com.globalshopper.GlobalShopper.entity.Fournisseur;
import com.globalshopper.GlobalShopper.repository.CrudMapper;
import com.globalshopper.GlobalShopper.repository.FournisseurRepository;

public class FournisseurService extends GenericCrudService<Fournisseur, Long, UtilisateurRequestDTO, UtilisateurResponseDTO> {
    public FournisseurService(FournisseurRepository repository, CrudMapper<Fournisseur, UtilisateurRequestDTO, UtilisateurResponseDTO> mapper) {
        super(repository, mapper);
    }
}
