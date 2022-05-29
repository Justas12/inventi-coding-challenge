package com.example.demo.model.view;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class BankOperationView {

    String accountNumber;

    LocalDateTime operationDateTime;

    String beneficiary;

    Long amount;

    String currency;

    String comment;

}