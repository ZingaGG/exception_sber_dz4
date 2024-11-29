package org.example.Model;

import lombok.Getter;
import lombok.Setter;
import org.example.Exceptions.AccountNotFoundException;
import org.example.Exceptions.AuthenticationException;
import org.example.Exceptions.InvalidAmountException;
import org.example.Interface.Terminal;

@Getter
@Setter
public class TerminalImpl implements Terminal {
    private long ID;
    private long balance = 0L;
    private boolean isAuthenticated = false;

    private final TerminalServer server;
    private final PinValidator pinValidator;

    public TerminalImpl(long ID, TerminalServer serverTerminal){
        this.ID = ID;
        this.server = serverTerminal;
        this.pinValidator = new PinValidator(this.ID);

        TerminalServer.getAccountBase().put(this.ID, this);
    }

    @Override
    public long checkBalance() throws AuthenticationException, AccountNotFoundException {
        if(!isAuthenticated){
            throw new AuthenticationException("Not Authenticated");
        }

        return this.server.getBalance(this.ID);
    }

    @Override
    public void depositMoney(Long amount) throws AuthenticationException, InvalidAmountException, AccountNotFoundException {
        if(!isAuthenticated){
            throw new AuthenticationException("Not Authenticated");
        }

        if(amount % 100 != 0){
            throw new InvalidAmountException("Wrong amount");
        }

        this.server.deposit(amount, this.ID);

    }

    @Override
    public void withdrawMoney(Long amount) throws AuthenticationException, InvalidAmountException, AccountNotFoundException {
        if(!isAuthenticated){
            throw new AuthenticationException("Authenticated");
        }

        if(amount % 100 != 0){
            throw new InvalidAmountException("Wrong amount");
        }

        this.server.withdraw(amount, this.ID);
    }
    @Override
    public boolean enterPin() {
        return false;
    }
}
