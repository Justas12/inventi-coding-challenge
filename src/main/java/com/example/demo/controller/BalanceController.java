package com.example.demo.controller;

import com.example.demo.model.query.BalanceQuery;
import com.example.demo.model.view.BalanceView;
import com.example.demo.service.BalanceService;
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
