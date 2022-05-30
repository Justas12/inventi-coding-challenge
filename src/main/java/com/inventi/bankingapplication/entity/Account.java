package com.inventi.bankingapplication.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "accounts")
@Setter
@Getter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "beneficiary")
    private String beneficiary;

    @OneToMany(mappedBy = "account")
    private List<Operation> operations;

}