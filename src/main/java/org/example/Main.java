package org.example;

import org.example.Exceptions.AccountIsLockedException;
import org.example.Exceptions.AccountNotFoundException;
import org.example.Exceptions.AuthenticationException;
import org.example.Exceptions.InvalidAmountException;
import org.example.Model.TerminalImpl;
import org.example.Model.TerminalServer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TerminalServer server = new TerminalServer();

        System.out.println("Welcome to my Terminal!");
        System.out.print("Enter a unique account ID: ");
        long accountId = scanner.nextLong();

        TerminalImpl terminal = new TerminalImpl(accountId, server);
        System.out.println("Account created successfully!");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Enter PIN");
            System.out.println("2. Check Balance");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1 -> {
                        terminal.enterPin();
                        System.out.println("Authentication successful!");
                    }
                    case 2 -> {
                        long balance = terminal.checkBalance(accountId);
                        System.out.println("Your current balance is: " + balance);
                    }
                    case 3 -> {
                        System.out.print("Enter the amount to deposit: ");
                        long depositAmount = scanner.nextLong();
                        terminal.depositMoney(depositAmount, accountId);
                        System.out.println("Deposit successful!");
                    }
                    case 4 -> {
                        System.out.print("Enter the amount to withdraw: ");
                        long withdrawAmount = scanner.nextLong();
                        terminal.withdrawMoney(withdrawAmount, accountId);
                        System.out.println("Withdrawal successful!");
                    }
                    case 5 -> {
                        System.out.println("Thank you for using my Terminal. Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (AuthenticationException | InvalidAmountException | AccountNotFoundException |
                     AccountIsLockedException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}