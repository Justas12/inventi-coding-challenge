package com.example.demo.entity;

import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bank_accounts")
@Setter
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "beneficiary")
    private String beneficiary;

    @OneToMany(mappedBy = "account")
    private List<BankAccountOperation> operations;

}