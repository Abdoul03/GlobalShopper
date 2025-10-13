package com.globalshopper.GlobalShopper.repository;

import com.globalshopper.GlobalShopper.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
