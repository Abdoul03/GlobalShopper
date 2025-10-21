package com.globalshopper.GlobalShopper.dto.request;

import com.globalshopper.GlobalShopper.entity.Caracteristique;
import com.globalshopper.GlobalShopper.entity.Categorie;
import com.globalshopper.GlobalShopper.entity.Fournisseur;
import com.globalshopper.GlobalShopper.entity.Media;
import com.globalshopper.GlobalShopper.entity.enums.UniteProduit;

import java.util.List;

public record ProduitRequestDTO(
        String nom,
        String description,
        double prix,
        int moq,
        int stock,
        UniteProduit unite,
        List<CaracteristiqueDTO> caracteristiques,
        Long categorieId

) {
}
