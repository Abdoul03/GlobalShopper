package com.globalshopper.GlobalShopper.dto.mapper;

import com.globalshopper.GlobalShopper.dto.request.UtilisateurRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.UtilisateurResponseDTO;
import com.globalshopper.GlobalShopper.entity.Utilisateur;

public class UtilisateurMapper {
    public static <T extends Utilisateur> T toEntity(UtilisateurRequestDTO dto, T userEntity) {
        userEntity.setNom(dto.getNom());
        userEntity.setPrenom(dto.getPrenom());
        userEntity.setUsername(dto.getUsername());
        userEntity.setTelephone(dto.getTelephone());
        userEntity.setEmail(dto.getEmail());
        userEntity.setMotDePasse(dto.getMotDePasse());
        userEntity.setRole(dto.getRole());
        return userEntity;
    }

    public static UtilisateurResponseDTO toDTO(Utilisateur user) {
        if (user == null) return null;

        UtilisateurResponseDTO dto = new UtilisateurResponseDTO();
        dto.setId(user.getId());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setUsername(user.getUsername());
        dto.setTelephone(user.getTelephone());
        dto.setEmail(user.getEmail());
        dto.setActif(user.isActif());
        dto.setPhotoUrl(user.getPhotoUrl());
        dto.setRole(user.getRole());
        return dto;
    }
}
