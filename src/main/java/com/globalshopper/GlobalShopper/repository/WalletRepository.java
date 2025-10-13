package com.globalshopper.GlobalShopper.repository;

import com.globalshopper.GlobalShopper.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
