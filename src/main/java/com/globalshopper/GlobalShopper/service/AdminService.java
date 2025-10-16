package com.globalshopper.GlobalShopper.service;


import com.globalshopper.GlobalShopper.dto.request.UtilisateurRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.UtilisateurResponseDTO;
import com.globalshopper.GlobalShopper.entity.Admin;
import com.globalshopper.GlobalShopper.entity.enums.Role;
import com.globalshopper.GlobalShopper.repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService{
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public Admin creatAdmin(Admin admin){
        Admin administrateur = new Admin();
        administrateur.setActif(true);
        administrateur.setMotDePasse(passwordEncoder.encode(admin.getMotDePasse()));
        administrateur.setRole(Role.ROLE_ADMIN);
        return adminRepository.save(administrateur);
    }

    public List<Admin> getAllAdmin(){
        return adminRepository.findAll();
    }

    public Admin getAAdmin(Long adminId){
        return adminRepository.findById(adminId).orElseThrow(()-> new EntityNotFoundException("Admin introuvable"));
    }

    public void deleteAdmin(Long adminId){
        Admin admin = adminRepository.findById(adminId).orElseThrow(()-> new EntityNotFoundException("Admin introuvable"));
        adminRepository.delete(admin);
    }
}
