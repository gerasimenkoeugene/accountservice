package com.callfire.task.accountservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private BigDecimal balance;

    public Account() {
    }

    public Account(Long id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void deposit(BigDecimal amount) {
        balance =  balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new RuntimeException("Overdraft. There are not enough money on the account id: " + id);
        }
        balance = balance.subtract(amount);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }
}
