package com.globalshopper.GlobalShopper.dto.response;

import com.globalshopper.GlobalShopper.entity.enums.Role;
import lombok.Data;

public record UtilisateurResponseDTO (
        long id,
        String nom,
        String prenom,
        String username,
        String telephone,
        String email,
        boolean actif,
        String photoUrl,
        Role role
){

}
