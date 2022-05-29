package com.example.demo.service.impl;

import com.example.demo.entity.BankAccount;
import com.example.demo.entity.BankAccountOperation;
import com.example.demo.model.CsvRecordBankStatement;
import com.example.demo.repository.BankAccountOperationRepository;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.service.ImportService;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

@RequiredArgsConstructor
@Service
public class CsvImportServiceImpl implements ImportService {

    private final BankAccountRepository bankAccountRepository;
    private final BankAccountOperationRepository bankAccountOperationRepository;

    @Override
    public void importFile(MultipartFile file) {
        try {
            CSVReader reader = buildCsvReader(buildReader(file.getBytes()));
            CsvToBean<CsvRecordBankStatement> csvReader = new CsvToBeanBuilder(reader)
                .withType(CsvRecordBankStatement.class)
                .withSeparator(',')
                .withIgnoreLeadingWhiteSpace(true)
                .withIgnoreEmptyLine(true)
                .build();
            Iterator<CsvRecordBankStatement> iterator = csvReader.stream().iterator();
            while (iterator.hasNext()) {
                CsvRecordBankStatement csvRecordBankStatement = iterator.next();
                BankAccount bankAccount = bankAccountRepository.findByNumber(csvRecordBankStatement.getAccountNumber())
                    .orElseGet(() -> {
                        BankAccount bankAccount1 = buildBankAccount(csvRecordBankStatement);
                        bankAccountRepository.save(bankAccount1);
                        return bankAccount1;
                    });
                BankAccountOperation bankAccountOperation = buildBankAccountOperation(csvRecordBankStatement, bankAccount);
                bankAccountOperationRepository.save(bankAccountOperation);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BankAccountOperation buildBankAccountOperation(CsvRecordBankStatement csvRecordBankStatement,
                                                           BankAccount bankAccount) {
        BankAccountOperation bankAccountOperation = new BankAccountOperation();
        bankAccountOperation.setAccount(bankAccount);
        bankAccountOperation.setAmount(csvRecordBankStatement.getAmount());
        bankAccountOperation.setComment(csvRecordBankStatement.getComment());
        bankAccountOperation.setCurrency(csvRecordBankStatement.getCurrency());
        bankAccountOperation.setDateTime(csvRecordBankStatement.getOperationDateTime());
        return bankAccountOperation;
    }

    private BankAccount buildBankAccount(CsvRecordBankStatement csvRecordBankStatement) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBeneficiary(csvRecordBankStatement.getBeneficiary());
        bankAccount.setNumber(csvRecordBankStatement.getAccountNumber());
        return bankAccount;
    }

    private CSVReader buildCsvReader(Reader reader) {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
        return new CSVReaderBuilder(reader).withCSVParser(csvParser).build();
    }

    private Reader buildReader(byte[] content) {
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content), StandardCharsets.UTF_8));
    }

}
