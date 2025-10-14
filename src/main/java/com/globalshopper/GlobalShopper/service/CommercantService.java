package com.globalshopper.GlobalShopper.service;

import com.globalshopper.GlobalShopper.controller.GenericCrudService;
import com.globalshopper.GlobalShopper.dto.request.UtilisateurRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.UtilisateurResponseDTO;
import com.globalshopper.GlobalShopper.entity.Commercant;
import com.globalshopper.GlobalShopper.repository.CommercantRepository;
import com.globalshopper.GlobalShopper.repository.CrudMapper;

public class CommercantService extends GenericCrudService<Commercant,Long, UtilisateurRequestDTO, UtilisateurResponseDTO> {
    public CommercantService(CommercantRepository repository, CrudMapper<Commercant, UtilisateurRequestDTO, UtilisateurResponseDTO> mapper) {
        super(repository, mapper);
    }
}
