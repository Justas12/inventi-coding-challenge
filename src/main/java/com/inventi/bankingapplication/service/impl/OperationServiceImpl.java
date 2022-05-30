package com.inventi.bankingapplication.service.impl;

import com.inventi.bankingapplication.entity.Account;
import com.inventi.bankingapplication.entity.Operation;
import com.inventi.bankingapplication.model.CsvRecordBankStatement;
import com.inventi.bankingapplication.model.query.OperationQuery;
import com.inventi.bankingapplication.model.view.OperationView;
import com.inventi.bankingapplication.model.view.FileView;
import com.inventi.bankingapplication.repository.OperationRepository;
import com.inventi.bankingapplication.repository.AccountRepository;
import com.inventi.bankingapplication.service.OperationService;
import com.inventi.bankingapplication.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class OperationServiceImpl implements OperationService {

    private final FileService fileService;
    private final OperationRepository operationRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public OperationServiceImpl(FileService fileService, OperationRepository operationRepository,
                                AccountRepository accountRepository) {
        this.fileService = fileService;
        this.operationRepository = operationRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public FileView exportToFile(OperationQuery query) {
        List<OperationView> operations = operationRepository.find(query)
            .stream()
            .map(this::buildOperationView)
            .collect(Collectors.toList());
        return fileService.write(operations, getFileName());
    }

    private String getFileName() {
        StringJoiner joiner = new StringJoiner("_");
        LocalDateTime now = LocalDateTime.now();
        return joiner
            .add("Operations")
            .add(LocalDate.now().toString())
            .add(now.getHour() + ":" + now.getMinute() + ":" + now.getSecond())
            .toString();
    }

    @Override
    public void importFromFile(MultipartFile file) {
        fileService.read(file, CsvRecordBankStatement.class).forEach(rec -> {
            Account account = accountRepository.findByNumber(rec.getAccountNumber())
                .orElseGet(() -> accountRepository.save(buildAccount(rec)));
            operationRepository.save(buildOperation(rec, account));
        });
    }

    private OperationView buildOperationView(Operation operation) {
        Account account = operation.getAccount();
        return OperationView.builder()
            .accountNumber(account.getNumber())
            .amount(operation.getAmount())
            .beneficiary(account.getBeneficiary())
            .operationDateTime(operation.getDateTime())
            .comment(operation.getComment())
            .currency(operation.getCurrency())
            .build();
    }

    private Operation buildOperation(CsvRecordBankStatement csvRecordBankStatement,
                                     Account account) {
        Operation operation = new Operation();
        operation.setAccount(account);
        operation.setAmount(csvRecordBankStatement.getAmount());
        operation.setComment(csvRecordBankStatement.getComment());
        operation.setCurrency(csvRecordBankStatement.getCurrency());
        operation.setDateTime(csvRecordBankStatement.getOperationDateTime());
        return operation;
    }

    private Account buildAccount(CsvRecordBankStatement csvRecordBankStatement) {
        Account account = new Account();
        account.setBeneficiary(csvRecordBankStatement.getBeneficiary());
        account.setNumber(csvRecordBankStatement.getAccountNumber());
        return account;
    }

}
