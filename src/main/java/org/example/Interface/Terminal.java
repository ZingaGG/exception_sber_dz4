package org.example.Interface;


import org.example.Exceptions.AccountNotFoundException;
import org.example.Exceptions.AuthenticationException;
import org.example.Exceptions.InvalidAmountException;

// Проверить состояние счета
// Снять / положить деньги
public interface Terminal {
    long checkBalance() throws AuthenticationException, AccountNotFoundException;
    void depositMoney(Long amount) throws AuthenticationException, InvalidAmountException, AccountNotFoundException;
    void withdrawMoney(Long amount) throws AuthenticationException, InvalidAmountException, AccountNotFoundException;
    boolean enterPin();

}
