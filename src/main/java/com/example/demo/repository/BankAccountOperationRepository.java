package com.example.demo.repository;

import com.example.demo.entity.BankAccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountOperationRepository extends JpaRepository<BankAccountOperation, Long> {
}