package com.globalshopper.GlobalShopper.dto.response;

import com.globalshopper.GlobalShopper.entity.enums.Role;
import lombok.Data;

@Data
public class UtilisateurResponseDTO {
    private long id;
    private String nom;
    private String prenom;
    private String username;
    private String telephone;
    private String email;
    private boolean actif;
    private String photoUrl;
    private Role role;
}
