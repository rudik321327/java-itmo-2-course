package com.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Account {
    private final String accountNumber;    // уникальный номер счёта
    private BigDecimal balance;            // баланс
    private List<String> transactionHistory; // история операций

    /**
     * create account with 0 balance
     * @param accountNumber namber of account
     */
    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = BigDecimal.ZERO;
        this.transactionHistory = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void addTransaction(String description) {
        this.transactionHistory.add(description);
    }
}
