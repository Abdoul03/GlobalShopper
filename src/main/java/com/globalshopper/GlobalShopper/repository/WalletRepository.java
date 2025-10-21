package com.globalshopper.GlobalShopper.repository;

import com.globalshopper.GlobalShopper.entity.Commercant;
import com.globalshopper.GlobalShopper.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByCommercant(Commercant commercant);
}
