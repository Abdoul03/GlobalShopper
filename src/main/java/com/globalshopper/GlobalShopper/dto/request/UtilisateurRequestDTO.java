package com.globalshopper.GlobalShopper.dto.request;

import com.globalshopper.GlobalShopper.entity.enums.Role;
import lombok.Data;

public record UtilisateurRequestDTO(
        String nom,
        String prenom,
        String username,
        String telephone,
        String email,
        String motDePasse,
        Role role
) {
}
