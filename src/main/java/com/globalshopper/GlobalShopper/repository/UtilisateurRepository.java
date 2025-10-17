package com.globalshopper.GlobalShopper.repository;

import com.globalshopper.GlobalShopper.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByTelephone(String telephone);
    Optional<Utilisateur> findByEmail(String email);
}
