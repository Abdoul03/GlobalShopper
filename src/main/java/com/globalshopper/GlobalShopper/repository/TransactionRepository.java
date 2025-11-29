package com.globalshopper.GlobalShopper.repository;

import com.globalshopper.GlobalShopper.entity.Transaction;
import com.globalshopper.GlobalShopper.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByWallet(Wallet wallet);
    List<Transaction> findByParticipationCommercantId(Long commercantId);
}
