package org.example.Model;

import org.example.Exceptions.AccountNotFoundException;
import org.example.Exceptions.InvalidAmountException;

import java.util.HashMap;


public class TerminalServer {
    private static final HashMap<Long, TerminalImpl> accountBase = new HashMap<>();

    public static HashMap<Long, TerminalImpl> getAccountBase() {
        return accountBase;
    }

    public void deposit(Long deposit, long id) throws AccountNotFoundException {
        TerminalImpl account = accountBase.getOrDefault(id, null);
        if(account == null){
            throw new AccountNotFoundException("Account does not exists");
        }
        long balance = account.getBalance();
        account.setBalance(balance + deposit);
    }

    public void withdraw(Long withdrawAmount, long id) throws AccountNotFoundException, InvalidAmountException {
        TerminalImpl account = accountBase.getOrDefault(id, null);
        if (account == null) {
            throw new AccountNotFoundException("Account does not exist.");
        }

        long balance = account.getBalance();
        if (balance < withdrawAmount) {
            throw new InvalidAmountException("Insufficient funds for withdrawal.");
        }

        account.setBalance(balance - withdrawAmount);
    }


    public long getBalance(long id) throws AccountNotFoundException {
        TerminalImpl account = accountBase.getOrDefault(id, null);
        if(account == null){
            throw new AccountNotFoundException("Account does not exists");
        }

        return account.getBalance();
    }
}
