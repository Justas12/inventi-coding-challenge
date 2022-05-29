package com.example.demo.model.query;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class BankAccountOperationsQuery {

    LocalDateTime from;
    LocalDateTime to;

}
