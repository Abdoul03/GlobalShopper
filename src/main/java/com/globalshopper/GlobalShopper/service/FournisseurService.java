package com.globalshopper.GlobalShopper.service;

import com.globalshopper.GlobalShopper.dto.mapper.FournisseurMapper;
import com.globalshopper.GlobalShopper.dto.request.FournisseurRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.FournisseurResponseDTO;
import com.globalshopper.GlobalShopper.entity.Fournisseur;
import com.globalshopper.GlobalShopper.entity.enums.Role;
import com.globalshopper.GlobalShopper.repository.FournisseurRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FournisseurService {
    private final FournisseurRepository fournisseurRepository;
    private final PasswordEncoder passwordEncoder;

    public FournisseurResponseDTO CreatFournisseur(FournisseurRequestDTO fournisseurDTO){
        Fournisseur fournisseur = FournisseurMapper.toEntity(fournisseurDTO, new Fournisseur());
        fournisseur.setRole(Role.ROLE_FOURNISSEUR);
        fournisseur.setMotDePasse(passwordEncoder.encode(fournisseurDTO.motDePasse()));
        fournisseurRepository.save(fournisseur);
        return FournisseurMapper.toResponse(fournisseur);
    }

    public List<FournisseurResponseDTO> getAllFournisseur(){
        List<Fournisseur> fournisseur = fournisseurRepository.findAll();
        return fournisseur.stream().map(FournisseurMapper :: toResponse).toList();
    }

    public FournisseurResponseDTO getFournisseur(Long idFournisseur){
        Fournisseur fournisseur = fournisseurRepository.findById(idFournisseur).orElseThrow(()-> new EntityNotFoundException("Fournisseur introuvable"));
        return FournisseurMapper.toResponse(fournisseur);
    }

    public FournisseurResponseDTO updateFournisseur(Long idFournisseur, FournisseurRequestDTO fournisseurRequestDTO){
        Fournisseur fournisseur = fournisseurRepository.findById(idFournisseur).orElseThrow(()-> new EntityNotFoundException("Fournisseur introuvable"));

        fournisseurRepository.save(fournisseur);
        return FournisseurMapper.toResponse(fournisseur);
    }

    public void deleteFournisseur (Long idFournisseur){
        fournisseurRepository.findById(idFournisseur).orElseThrow(()-> new EntityNotFoundException("Fournisseur introuvable"));
    }
}
