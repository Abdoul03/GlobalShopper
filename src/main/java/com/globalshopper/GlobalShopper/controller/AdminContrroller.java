package com.globalshopper.GlobalShopper.controller;

import com.globalshopper.GlobalShopper.dto.request.UtilisateurRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.UtilisateurResponseDTO;
import com.globalshopper.GlobalShopper.entity.Admin;
import com.globalshopper.GlobalShopper.service.AdminService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminContrroller extends GenericCrudController<Admin,Long, UtilisateurRequestDTO, UtilisateurResponseDTO>{
    public AdminContrroller(AdminService adminService) {
        super(adminService);
    }
}
