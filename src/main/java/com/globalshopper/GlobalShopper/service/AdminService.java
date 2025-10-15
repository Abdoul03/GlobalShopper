package com.globalshopper.GlobalShopper.service;

import com.globalshopper.GlobalShopper.dto.mapper.AdminMapper;
import com.globalshopper.GlobalShopper.dto.request.UtilisateurRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.UtilisateurResponseDTO;
import com.globalshopper.GlobalShopper.entity.Admin;
import com.globalshopper.GlobalShopper.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService extends GenericCrudService<Admin,Long, UtilisateurRequestDTO, UtilisateurResponseDTO> {
    public AdminService(AdminRepository repository, AdminMapper mapper) {
        super(repository, mapper);
    }
}
