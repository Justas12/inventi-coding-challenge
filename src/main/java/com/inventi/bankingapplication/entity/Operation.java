package com.inventi.bankingapplication.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "operations")
@Setter
@Getter
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}