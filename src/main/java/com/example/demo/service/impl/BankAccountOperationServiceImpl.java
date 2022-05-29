package com.example.demo.service.impl;

import com.example.demo.entity.BankAccount;
import com.example.demo.entity.BankAccountOperation;
import com.example.demo.model.CsvRecordBankStatement;
import com.example.demo.model.query.BankAccountOperationsQuery;
import com.example.demo.model.view.BankOperationView;
import com.example.demo.model.view.FileView;
import com.example.demo.repository.BankAccountOperationRepository;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.service.BankAccountOperationService;
import com.example.demo.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankAccountOperationServiceImpl implements BankAccountOperationService {

    private final FileService fileService;
    private final BankAccountOperationRepository bankAccountOperationRepository;
    private final BankAccountRepository bankAccountRepository;

    @Override
    public FileView exportToFile(BankAccountOperationsQuery query) {
        List<BankOperationView> operations = bankAccountOperationRepository.find(query)
            .stream()
            .map(this::buildBankOperationView)
            .collect(Collectors.toList());
        return fileService.write(operations, "zaza");
    }

    @Override
    public void importFromFile(MultipartFile file) {
        fileService.read(file, CsvRecordBankStatement.class).forEach(rec -> {
            BankAccount account = bankAccountRepository.findByNumber(rec.getAccountNumber())
                .orElseGet(() -> bankAccountRepository.save(buildBankAccount(rec)));
            bankAccountOperationRepository.save(buildBankAccountOperation(rec, account));
        });
    }

    private BankOperationView buildBankOperationView(BankAccountOperation bankAccountOperation) {
        BankAccount account = bankAccountOperation.getAccount();
        return BankOperationView.builder()
            .accountNumber(account.getNumber())
            .amount(bankAccountOperation.getAmount())
            .beneficiary(account.getBeneficiary())
            .operationDateTime(bankAccountOperation.getDateTime())
            .comment(bankAccountOperation.getComment())
            .currency(bankAccountOperation.getCurrency())
            .build();
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

}
