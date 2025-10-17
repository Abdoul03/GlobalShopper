package com.globalshopper.GlobalShopper.service;

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

    public Categorie createCategorie (Categorie categorie){
        return repository.save(categorie);
    }

    public List<Categorie> getAllCategorie(){
        return repository.findAll();
    }

    public Categorie getACategorie(Long id){
        return repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Categorie nontrouver"));
    }

    public Categorie updateCategorie(Long id, Categorie categorie){
        Categorie categorie1 = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Categorie nontrouver"));
        repository.save(categorie1);
        return categorie1;
    }

    public void deleteCategorie(long id){
        Categorie categorie = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Categorie nontrouver"));
        repository.delete(categorie);
    }
}
