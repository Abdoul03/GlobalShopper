package com.globalshopper.GlobalShopper.repository;

import com.globalshopper.GlobalShopper.entity.CommandeGroupee;
import com.globalshopper.GlobalShopper.entity.Produit;
import com.globalshopper.GlobalShopper.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommandeGroupeeRepository extends JpaRepository<CommandeGroupee, Integer> {
    Optional<CommandeGroupee> findByProduitAndStatus(Produit produit, OrderStatus orderStatus);
}
