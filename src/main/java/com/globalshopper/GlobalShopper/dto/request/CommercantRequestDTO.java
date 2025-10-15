package com.globalshopper.GlobalShopper.dto.request;

import com.globalshopper.GlobalShopper.entity.Participation;
import com.globalshopper.GlobalShopper.entity.enums.Role;

import java.util.List;

public record CommercantRequestDTO (
        String nom,
        String prenom,
        String username,
        String telephone,
        String email,
        String motDePasse,
        boolean actif,
        Role role
){

}
