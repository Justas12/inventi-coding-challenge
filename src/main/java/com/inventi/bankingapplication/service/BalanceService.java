package com.inventi.bankingapplication.service;

import com.inventi.bankingapplication.model.query.BalanceQuery;
import com.inventi.bankingapplication.model.view.BalanceView;

public interface BalanceService {
    BalanceView find(BalanceQuery query);
}
