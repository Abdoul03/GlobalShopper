package com.globalshopper.GlobalShopper.service;

import com.globalshopper.GlobalShopper.controller.GenericCrudService;
import com.globalshopper.GlobalShopper.dto.request.UtilisateurRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.UtilisateurResponseDTO;
import com.globalshopper.GlobalShopper.entity.Admin;
import com.globalshopper.GlobalShopper.repository.AdminRepository;
import com.globalshopper.GlobalShopper.repository.CrudMapper;
import org.springframework.stereotype.Service;

@Service
public class AdminService extends GenericCrudService<Admin,Long, UtilisateurRequestDTO, UtilisateurResponseDTO> {
    public AdminService(AdminRepository repository, CrudMapper<Admin, UtilisateurRequestDTO, UtilisateurResponseDTO> mapper) {
        super(repository, mapper);
    }
}
