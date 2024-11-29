package org.example.Exceptions;

public class AccountIsLockedException extends Exception{
    public AccountIsLockedException(String message){
        super(message);
    }
}
