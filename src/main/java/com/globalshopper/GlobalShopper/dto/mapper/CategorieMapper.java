package com.globalshopper.GlobalShopper.dto.mapper;

import com.globalshopper.GlobalShopper.dto.request.CategorieRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.CategorieResponseDTO;
import com.globalshopper.GlobalShopper.entity.Categorie;

public class CategorieMapper {
    public static Categorie toEntity(CategorieRequestDTO dto, Categorie categorie){
        if (dto == null) return null;
        Categorie cate = new Categorie();
        cate.setNom(dto.nom());
        return cate;
    }

    public static CategorieResponseDTO toResponse(Categorie categorie){
        if (categorie == null) return null;
        return new CategorieResponseDTO(
                categorie.getId(),
                categorie.getNom()
        );
    }
}
