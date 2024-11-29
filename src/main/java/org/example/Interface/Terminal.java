package org.example.Interface;


import org.example.Exceptions.AccountIsLockedException;
import org.example.Exceptions.AccountNotFoundException;
import org.example.Exceptions.AuthenticationException;
import org.example.Exceptions.InvalidAmountException;

// Проверить состояние счета
// Снять / положить деньги
public interface Terminal {
    long checkBalance(Long ID) throws AuthenticationException, AccountNotFoundException;
    void depositMoney(Long amount, Long ID) throws AuthenticationException, InvalidAmountException, AccountNotFoundException;
    void withdrawMoney(Long amount, Long ID) throws AuthenticationException, InvalidAmountException, AccountNotFoundException;
    void enterPin() throws AccountIsLockedException;

}
