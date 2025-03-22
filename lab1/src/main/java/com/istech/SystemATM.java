package com.istech;

import java.math.BigDecimal;
import java.util.Scanner;
/**
 * Точка входа в приложение (консольный интерфейс)
 */
public class SystemATM {
    public static void main(String[] args) {
        ATM atm = new ATM();
        Scanner scanner = new Scanner(System.in);

        System.out.println("welcome!");

        while (true) {
            System.out.println("\nselect option:");
            System.out.println("1. create account");
            System.out.println("2. show balance");
            System.out.println("3. withdraw");
            System.out.println("4. indraw");
            System.out.println("5. show operations");
            System.out.println("6. exit");

            System.out.print("you choose: ");
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("number of account: ");
                        String newAcc = scanner.nextLine().trim();
                        atm.createAccount(newAcc);
                        System.out.println("account " + newAcc + " created!");
                        break;
                    case "2":
                        System.out.print("number of account: ");
                        String accBalance = scanner.nextLine().trim();
                        System.out.println("balance: " + atm.getBalance(accBalance));
                        break;
                    case "3":
                        System.out.print("number of account: ");
                        String accWithdraw = scanner.nextLine().trim();
                        System.out.print("sum of withdraw: ");
                        BigDecimal amountW = new BigDecimal(scanner.nextLine().trim());
                        atm.withdraw(accWithdraw, amountW);
                        System.out.println("withdraw successful");
                        break;
                    case "4":
                        System.out.print("account number: ");
                        String accDeposit = scanner.nextLine().trim();
                        System.out.print("sum of indraw: ");
                        BigDecimal amountD = new BigDecimal(scanner.nextLine().trim());
                        atm.deposit(accDeposit, amountD);
                        System.out.println("indraw successful");
                        break;
                    case "5":
                        System.out.print("number of wallet: ");
                        String accHistory = scanner.nextLine().trim();
                        System.out.println(atm.getTransactionHistory(accHistory));
                        break;
                    case "6":
                        System.out.println("exit");
                        return;
                    default:
                        System.out.println("error:");
                }
            } catch (InsufficientFundsException e) {
                System.out.println("error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("error: " + e.getMessage());
            }
        }
    }
}
