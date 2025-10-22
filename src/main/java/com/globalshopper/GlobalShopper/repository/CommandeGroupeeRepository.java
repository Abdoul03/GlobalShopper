package com.globalshopper.GlobalShopper.repository;

import com.globalshopper.GlobalShopper.entity.CommandeGroupee;
import com.globalshopper.GlobalShopper.entity.Commercant;
import com.globalshopper.GlobalShopper.entity.Produit;
import com.globalshopper.GlobalShopper.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommandeGroupeeRepository extends JpaRepository<CommandeGroupee, Integer> {
    Optional<CommandeGroupee> findByProduitAndStatus(Produit produit, OrderStatus orderStatus);
    Optional<CommandeGroupee> findByCommercantId(long commercantId);
    Optional<List<CommandeGroupee>> findAllByCommercantId(long commercantId);
}
