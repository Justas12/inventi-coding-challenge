package com.example.demo.repository;

import com.example.demo.entity.Operation;
import com.example.demo.model.query.OperationQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    @Query(
        "from Operation o " +
            "where (:#{#query.from} is null or o.dateTime >= :#{#query.from}) " +
            "and (:#{#query.to} is null or o.dateTime <= :#{#query.to})"
    )
    List<Operation> find(OperationQuery query);
}