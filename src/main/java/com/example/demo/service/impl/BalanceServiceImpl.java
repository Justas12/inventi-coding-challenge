package com.example.demo.service.impl;

import com.example.demo.entity.Account;
import com.example.demo.entity.Operation;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.query.BalanceQuery;
import com.example.demo.model.view.BalanceView;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final AccountRepository accountRepository;

    @Override
    public BalanceView find(BalanceQuery query) {
        String accountNumber = query.getAccountNumber();
        LocalDateTime from = query.getFrom();
        LocalDateTime to = query.getTo();
        Account account = accountRepository.findByNumber(accountNumber).orElseThrow(() -> new EntityNotFoundException(accountNumber));
        List<Operation> operations = account.getOperations();
        Long balance = operations.stream()
            .filter(o -> (from == null || o.getDateTime().isAfter(from)) && (to == null || o.getDateTime().isBefore(to)))
            .map(Operation::getAmount)
            .reduce(0L, Long::sum);
        return BalanceView.builder().balance(balance).build();
    }
}
