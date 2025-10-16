package com.globalshopper.GlobalShopper.service;

import com.globalshopper.GlobalShopper.entity.Pays;
import com.globalshopper.GlobalShopper.repository.PaysRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaysService {
    private final PaysRepository paysRepository;

    public Pays addCountry(Pays pays){
        return paysRepository.save(pays);
    }

    public List<Pays> getAllCountry(){
        return paysRepository.findAll();
    }

    public Pays getACountry(long idCountry){
        return paysRepository.findById(idCountry).orElseThrow(()-> new EntityNotFoundException("Pays introuvable"));
    }

    public Pays update(long idCountry, Pays pays){
        Pays country = paysRepository.findById(idCountry).orElseThrow(()-> new EntityNotFoundException("Pays introuvable"));
        country.setNom(pays.getNom());
        return paysRepository.save(country);
    }

    public void deletePays(long idCountry){
        Pays country = paysRepository.findById(idCountry).orElseThrow(()-> new EntityNotFoundException("Pays introuvable"));
    }
}
