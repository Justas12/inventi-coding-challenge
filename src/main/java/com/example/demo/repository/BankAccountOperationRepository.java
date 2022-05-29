package com.example.demo.repository;

import com.example.demo.entity.BankAccountOperation;
import com.example.demo.model.query.BankAccountOperationsQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BankAccountOperationRepository extends JpaRepository<BankAccountOperation, Long> {
    @Query(
        "from BankAccountOperation bao " +
            "where (:#{#query.from} is null or bao.dateTime >= :#{#query.from}) " +
            "and (:#{#query.to} is null or bao.dateTime <= :#{#query.to})"
    )
    List<BankAccountOperation> find(BankAccountOperationsQuery query);
}