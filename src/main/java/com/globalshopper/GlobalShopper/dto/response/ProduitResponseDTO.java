package com.globalshopper.GlobalShopper.dto.response;

import com.globalshopper.GlobalShopper.entity.*;
import com.globalshopper.GlobalShopper.entity.enums.UniteProduit;

import java.util.List;

public record ProduitResponseDTO(
        long id,
        String nom,
        String description,
        int prix,
        int moq,
        int stock,
        UniteProduit unite,
        CategorieResponseDTO categorie,
        List<CaracteristiqueResponseDTO> caracteristiques,
        List<MediaResponseDTO> media,
        FournisseurResponseDTO fournisseur,
        List<CommandeGroupee> commandeGroupees
) {
}
