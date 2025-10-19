package com.globalshopper.GlobalShopper.dto.request;

import com.globalshopper.GlobalShopper.entity.Pays;
import com.globalshopper.GlobalShopper.entity.enums.Role;

public record FournisseurRequestDTO (
        String nom,
        String prenom,
        String username,
        String telephone,
        String email,
        String motDePasse,
        boolean actif,
        Role role,
        Pays pays
){
}
