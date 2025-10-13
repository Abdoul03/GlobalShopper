package com.globalshopper.GlobalShopper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository <T, ID> {
    // Ajouter une entité
    T ajout(T entity);

    // Liste toutes les entités
    List<T> liste();

    // Trouver une entité par son ID
    Optional<T> trouverParId(ID id);

    // Mettre à jour une entité existante
    T miseAJour(T entity, ID id);

    // Supprimer une entité par son ID
    void supprimer(ID id);
}
