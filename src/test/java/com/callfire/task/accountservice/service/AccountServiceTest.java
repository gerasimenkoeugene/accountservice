package com.callfire.task.accountservice.service;

import com.callfire.task.accountservice.Application;
import com.callfire.task.accountservice.entity.Account;
import com.callfire.task.accountservice.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;
    @MockBean
    private AccountRepository accountRepository;
    private Account firstAccount;
    private Account secondAccount;
    private BigDecimal transferAmount;

    @Before
    public void setUp() {
        firstAccount = new Account(1L, new BigDecimal("1000"));
        secondAccount = new Account(2L, new BigDecimal("1000"));
        transferAmount = new BigDecimal("500");
        Mockito.when(accountRepository.save(firstAccount))
                .thenReturn(firstAccount);
        Mockito.when(accountRepository.save(secondAccount))
                .thenReturn(secondAccount);
    }

    @Test
    public void testTransfer() {
        accountService.transferAmount(firstAccount , secondAccount, transferAmount);
        assertEquals(new BigDecimal("500"), firstAccount.getBalance());
        assertEquals(new BigDecimal("1500"), secondAccount.getBalance());
        accountService.transferAmount(secondAccount , firstAccount, transferAmount);
        assertEquals(firstAccount.getBalance(), secondAccount.getBalance());
    }

    @Test
    public void testSyncTransfer() throws InterruptedException {
        Thread thread1 = new Thread(() -> accountService.transferAmount(firstAccount, secondAccount, transferAmount));
        Thread thread2 = new Thread(() -> accountService.transferAmount(secondAccount, firstAccount, transferAmount));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertEquals(firstAccount.getBalance(), secondAccount.getBalance());
    }



}
