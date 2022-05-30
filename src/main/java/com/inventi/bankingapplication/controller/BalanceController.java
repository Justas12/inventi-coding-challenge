package com.inventi.bankingapplication.controller;

import com.inventi.bankingapplication.model.query.BalanceQuery;
import com.inventi.bankingapplication.model.view.BalanceView;
import com.inventi.bankingapplication.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/balances")
public class BalanceController {

    private final BalanceService service;

    @GetMapping
    public BalanceView find(BalanceQuery query) {
        return service.find(query);
    }

}
