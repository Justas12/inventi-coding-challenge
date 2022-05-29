package com.example.demo.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CsvRecordBankStatement {

    @CsvBindByName(column = "ACCOUNT_NUMBER", required = true)
    private String accountNumber;

    @CsvBindByName(column = "OPERATION_DATE_TIME", required = true)
    @CsvDate(value = "yyyy-MM-dd HH:mm[:ss]")
    private LocalDateTime operationDateTime;

    @CsvBindByName(column = "BENEFICIARY", required = true)
    private String beneficiary;

    @CsvBindByName(column = "AMOUNT", required = true)
    private Long amount;

    @CsvBindByName(column = "CURRENCY", required = true)
    private String currency;

    @CsvBindByName(column = "COMMENT")
    private String comment;

}