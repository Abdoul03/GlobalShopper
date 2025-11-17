package com.globalshopper.GlobalShopper.service;

import com.globalshopper.GlobalShopper.dto.mapper.CommercantMapp;
import com.globalshopper.GlobalShopper.dto.request.CommercantRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.CommercantResponseDTO;

import com.globalshopper.GlobalShopper.entity.Commercant;
import com.globalshopper.GlobalShopper.entity.Pays;
import com.globalshopper.GlobalShopper.entity.enums.Role;
import com.globalshopper.GlobalShopper.repository.CommercantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommercantService {

    public CommercantService(CommercantRepository commercantRepository, PasswordEncoder passwordEncoder) {
        this.commercantRepository = commercantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private final CommercantRepository commercantRepository;
    private final PasswordEncoder passwordEncoder;

    //Inscription
    public CommercantResponseDTO inscription (CommercantRequestDTO commercant){
        Commercant commer = CommercantMapp.toEntity(commercant, new Commercant());
        commer.setActif(true);
        commer.setRole(Role.ROLE_COMMERCANT);
        commer.setMotDePasse(passwordEncoder.encode(commercant.motDePasse()));
        commercantRepository.save(commer);
        return CommercantMapp.toResponse(commer);
    }

    //Get All
    public List<CommercantResponseDTO> getAllCommercant(){
        List<Commercant> commercants = commercantRepository.findAll();
        return commercants.stream().map(CommercantMapp :: toResponse).toList();
    }

    //Get by id
    public CommercantResponseDTO getByCommercantId (long commercantId){
        Commercant commercant = commercantRepository.findById(commercantId).orElseThrow(()-> new EntityNotFoundException("Commercant introuvable"));
        return CommercantMapp.toResponse(commercant);
    }

    //Update
    public CommercantResponseDTO updateCommercant (long commercantId, CommercantRequestDTO commercantRequestDTO){
        Commercant commercant = commercantRepository.findById(commercantId).orElseThrow(()-> new EntityNotFoundException("Commercant introuvable"));

        commercant.setNom(commercantRequestDTO.nom());
        commercant.setPrenom(commercantRequestDTO.prenom());
        commercant.setUsername(commercantRequestDTO.username());
        commercant.setTelephone(commercantRequestDTO.telephone());
        commercant.setEmail(commercantRequestDTO.email());

        if (commercantRequestDTO.motDePasse() != null && !commercantRequestDTO.motDePasse().isBlank()) {
            commercant.setMotDePasse(passwordEncoder.encode(commercantRequestDTO.motDePasse()));
        }

        commercantRepository.save(commercant);
        return CommercantMapp.toResponse(commercant);
    }

    //Delete
    public boolean deleteCommercant(long commercantId) {
        Commercant commercant = commercantRepository.findById(commercantId).orElseThrow(()-> new EntityNotFoundException("Commercant introuvable"));
        commercantRepository.delete(commercant);
        return true;
    }
}
