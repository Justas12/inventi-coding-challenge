package com.example.demo.service;

import com.example.demo.model.query.BalanceQuery;
import com.example.demo.model.view.BalanceView;

public interface BalanceService {
    BalanceView find(BalanceQuery query);
}
