package com.istech;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Набор тестов для сервиса ATM
 */
class ATMTest {
    private ATM atm;

    @BeforeEach
    void setUp() {
        atm = new ATM();
    }

    @Test
    @DisplayName("Проверка создания счёта")
    void testCreateAccount() {
        Account account = atm.createAccount("ACC1");
        assertNotNull(account, "Должен вернуться объект счёта");
        assertEquals("ACC1", account.getAccountNumber(), "Номер счёта не совпадает");
        assertEquals(BigDecimal.ZERO, account.getBalance(), "Начальный баланс должен быть 0");
    }

    @Test
    @DisplayName("Пополнение счёта должно увеличивать баланс")
    void testDeposit() {
        atm.createAccount("ACC2");
        atm.deposit("ACC2", new BigDecimal("1000.00"));
        assertEquals(new BigDecimal("1000.00"), atm.getBalance("ACC2"));
    }

    @Test
    @DisplayName("Снятие денег со счёта корректно уменьшает баланс")
    void testWithdraw() throws InsufficientFundsException {
        atm.createAccount("ACC3");
        atm.deposit("ACC3", new BigDecimal("500.00"));
        atm.withdraw("ACC3", new BigDecimal("200.00"));
        assertEquals(new BigDecimal("300.00"), atm.getBalance("ACC3"));
    }

    @Test
    @DisplayName("Снятие денег не должно проходить при недостатке средств")
    void testWithdrawInsufficientFunds() {
        atm.createAccount("ACC4");
        atm.deposit("ACC4", new BigDecimal("100.00"));
        assertThrows(InsufficientFundsException.class, () ->
                        atm.withdraw("ACC4", new BigDecimal("200.00")),
                "Ожидается исключение InsufficientFundsException");
    }

    @Test
    @DisplayName("Просмотр истории операций содержит все операции")
    void testTransactionHistory() throws InsufficientFundsException {
        atm.createAccount("ACC5");
        atm.deposit("ACC5", new BigDecimal("100.00"));
        atm.withdraw("ACC5", new BigDecimal("50.00"));

        String history = atm.getTransactionHistory("ACC5");
        assertTrue(history.contains("DEPOSIT: 100.00"));
        assertTrue(history.contains("WITHDRAW: 50.00"));
    }

    @Test
    @DisplayName("Снятие отрицательной суммы должно вызывать IllegalArgumentException")
    void testWithdrawNegativeAmount() {
        atm.createAccount("ACC_NEG");
        // Положим на счет 500.00, чтобы не было недостатка средств
        atm.deposit("ACC_NEG", new BigDecimal("500.00"));

        assertThrows(
                IllegalArgumentException.class,
                () -> atm.withdraw("ACC_NEG", new BigDecimal("-100.00")),
                "Ожидается IllegalArgumentException при попытке снять отрицательную сумму"
        );
    }

    @Test
    @DisplayName("Пополнение отрицательной суммы должно вызывать IllegalArgumentException")
    void testDepositNegativeAmount() {
        atm.createAccount("ACC_NEG_DEP");

        assertThrows(
                IllegalArgumentException.class,
                () -> atm.deposit("ACC_NEG_DEP", new BigDecimal("-50.00")),
                "Ожидается IllegalArgumentException при попытке пополнить на отрицательную сумму"
        );
    }

}
