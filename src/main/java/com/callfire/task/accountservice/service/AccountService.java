package com.callfire.task.accountservice.service;

import com.callfire.task.accountservice.entity.Account;

import java.math.BigDecimal;

public interface AccountService {
    void transferAmount(Account from, Account to, BigDecimal amount);
}
