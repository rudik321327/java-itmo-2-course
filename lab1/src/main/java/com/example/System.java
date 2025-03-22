package com.example;

import java.math.BigDecimal;
import java.util.Scanner;
/**
 * Точка входа в приложение (консольный интерфейс)
 */
public class System {
    public static void main(String[] args) {
        ATM atm = new ATM();
        Scanner scanner = new Scanner(java.lang.System.in);

        java.lang.System.out.println("welcome!");

        while (true) {
            java.lang.System.out.println("\nselect option:");
            java.lang.System.out.println("1. create account");
            java.lang.System.out.println("2. show balance");
            java.lang.System.out.println("3. withdraw");
            java.lang.System.out.println("4. indraw");
            java.lang.System.out.println("5. show operations");
            java.lang.System.out.println("6. exit");

            java.lang.System.out.print("you choose: ");
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        java.lang.System.out.print("number of account: ");
                        String newAcc = scanner.nextLine().trim();
                        atm.createAccount(newAcc);
                        java.lang.System.out.println("account " + newAcc + " created!");
                        break;
                    case "2":
                        java.lang.System.out.print("number of account: ");
                        String accBalance = scanner.nextLine().trim();
                        java.lang.System.out.println("balance: " + atm.getBalance(accBalance));
                        break;
                    case "3":
                        java.lang.System.out.print("number of account: ");
                        String accWithdraw = scanner.nextLine().trim();
                        java.lang.System.out.print("sum of withdraw: ");
                        BigDecimal amountW = new BigDecimal(scanner.nextLine().trim());
                        atm.withdraw(accWithdraw, amountW);
                        java.lang.System.out.println("withdraw succesful");
                        break;
                    case "4":
                        java.lang.System.out.print("account number");
                        String accDeposit = scanner.nextLine().trim();
                        java.lang.System.out.print("sum of indraw ");
                        BigDecimal amountD = new BigDecimal(scanner.nextLine().trim());
                        atm.deposit(accDeposit, amountD);
                        java.lang.System.out.println("indraw succesful");
                        break;
                    case "5":
                        java.lang.System.out.print("number of wallet");
                        String accHistory = scanner.nextLine().trim();
                        java.lang.System.out.println(atm.getTransactionHistory(accHistory));
                        break;
                    case "6":
                        java.lang.System.out.println("exit");
                        return;
                    default:
                        java.lang.System.out.println("error:");
                }
            } catch (InsufficientFundsException e) {
                java.lang.System.out.println("error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                java.lang.System.out.println("error: " + e.getMessage());
            }
        }
    }
}
