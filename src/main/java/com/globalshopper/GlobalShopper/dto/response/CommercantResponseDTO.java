package com.globalshopper.GlobalShopper.dto.response;

import com.globalshopper.GlobalShopper.entity.Participation;
import com.globalshopper.GlobalShopper.entity.Pays;
import com.globalshopper.GlobalShopper.entity.enums.Role;

import java.util.List;

public record CommercantResponseDTO(
        long id,
        String nom,
        String prenom,
        String username,
        String telephone,
        String email,
        boolean actif,
        String photoUrl,
        PaysResponseDTO pays,
        Role role
) {

}
