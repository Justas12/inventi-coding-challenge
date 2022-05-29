package com.example.demo.model.query;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Value
@Builder
public class BalanceQuery {

    @NonNull
    String accountNumber;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime from;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime to;

}
