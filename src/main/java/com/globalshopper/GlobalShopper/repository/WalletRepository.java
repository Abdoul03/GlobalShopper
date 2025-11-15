package com.globalshopper.GlobalShopper.repository;

import com.globalshopper.GlobalShopper.entity.Commercant;
import com.globalshopper.GlobalShopper.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByNumero(String numero);
    @Query("SELECT w FROM Wallet w")
    Optional<Wallet> findSystemWallet();
}
