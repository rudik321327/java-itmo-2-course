package com.istech;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class ATM {
    private Map<String, Account> accounts = new HashMap<>();

    /**
     * registration
     * @param accountNumber is number of account
     * @return created object Account
     */
    public Account createAccount(String accountNumber) {
        if (accounts.containsKey(accountNumber)) {
            throw new IllegalArgumentException("Счёт с номером " + accountNumber + " уже существует.");
        }

        Account account = new Account(accountNumber);
        accounts.put(accountNumber, account);
        return account;
    }

    /**
     * return balance
     * @param accountNumber number of account
     * @return balance at the moment
     * @throws IllegalArgumentException if account not founded
     */
    public BigDecimal getBalance(String accountNumber) {
        Account account = getAccountOrThrow(accountNumber);
        return account.getBalance();
    }

    /**
     * withdraw
     * @param accountNumber number of account
     * @param amount sum of withdraw
     * @throws InsufficientFundsException if not enough of money
     */
    public void withdraw(String accountNumber, BigDecimal amount)
            throws InsufficientFundsException {
        Account account = getAccountOrThrow(accountNumber);
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("balace should be >= 0.");
        }
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("not enough money: " + account.getBalance());
        }
        BigDecimal newBalance = account.getBalance().subtract(amount);
        account.setBalance(newBalance);
        account.addTransaction("WITHDRAW: " + amount + " | Balance: " + newBalance);
    }

    /**
     * indraw
     * @param accountNumber number of account
     * @param amount sum of indraw
     */
    public void deposit(String accountNumber, BigDecimal amount) {
        Account account = getAccountOrThrow(accountNumber);
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("sum of indraw should be >= 0.");
        }
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        account.addTransaction("DEPOSIT: " + amount + " | Balance: " + newBalance);
    }

    /**
     * return history of operations
     * @param accountNumber number of account
     * @return list of operations
     * @throws IllegalArgumentException if account not founded
     */
    public String getTransactionHistory(String accountNumber) {
        Account account = getAccountOrThrow(accountNumber);
        StringBuilder sb = new StringBuilder("history of operations:\n");
        for (String record : account.getTransactionHistory()) {
            sb.append(record).append("\n");
        }
        return sb.toString();
    }


    private Account getAccountOrThrow(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account " + accountNumber + " not founded");
        }
        return account;
    }
}
