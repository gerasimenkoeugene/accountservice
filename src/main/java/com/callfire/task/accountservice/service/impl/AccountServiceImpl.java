package com.callfire.task.accountservice.service.impl;

import com.callfire.task.accountservice.entity.Account;
import com.callfire.task.accountservice.repository.AccountRepository;
import com.callfire.task.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    public void  transferAmount(Account from, Account to, BigDecimal amount) {
        Account first = from;
        Account second = to;
        //avoiding deadlocks by sorting
        if (first.getId() < second.getId()) {
            // Swap them
            first = to;
            second = from;
        }
        synchronized(first){
            synchronized(second){
                transferInTransaction(from, to, amount);
            }
        }
    }
    @Transactional
    private void transferInTransaction(Account from, Account to, BigDecimal amount) {
        from.withdraw(amount);
        to.deposit(amount);
        accountRepository.save(from);
        accountRepository.save(to);
    }
}
