package com.globalshopper.GlobalShopper.service;

import com.globalshopper.GlobalShopper.dto.mapper.CategorieMapper;
import com.globalshopper.GlobalShopper.dto.request.CategorieRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.CategorieResponseDTO;
import com.globalshopper.GlobalShopper.entity.Categorie;
import com.globalshopper.GlobalShopper.repository.CategorieRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieService {
    public CategorieService(CategorieRepository repository) {
        this.repository = repository;
    }

    private final CategorieRepository repository;

    public CategorieResponseDTO createCategorie (CategorieRequestDTO categorie){
        Categorie cate = CategorieMapper.toEntity(categorie, new Categorie());
        return CategorieMapper.toResponse(cate);
    }

    public List<CategorieResponseDTO> getAllCategorie(){
        List<Categorie> categorie = repository.findAll();
        return categorie.stream().map(CategorieMapper :: toResponse).toList();
    }

    public CategorieResponseDTO getACategorie(Long id){
         Categorie categorie = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Categorie nontrouver"));
        return CategorieMapper.toResponse(categorie);
    }

    public CategorieResponseDTO updateCategorie(Long id, CategorieRequestDTO categorie){
        Categorie categorie1 = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Categorie nontrouver"));
        repository.save(categorie1);
        return CategorieMapper.toResponse(categorie1);
    }

    public void deleteCategorie(long id){
        Categorie categorie = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Categorie nontrouver"));
        repository.delete(categorie);
    }
}
