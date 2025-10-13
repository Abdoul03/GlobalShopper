package com.globalshopper.GlobalShopper.dto.request;

import com.globalshopper.GlobalShopper.entity.enums.Role;
import lombok.Data;

@Data
public class UtilisateurRequestDTO {
    private String nom;
    private String prenom;
    private String username;
    private String telephone;
    private String email;
    private String motDePasse;
    private Role role;
}
