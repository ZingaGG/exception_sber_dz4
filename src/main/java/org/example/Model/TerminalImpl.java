package org.example.Model;

import lombok.Getter;
import lombok.Setter;
import org.example.Exceptions.*;
import org.example.Interface.Terminal;

import java.time.Instant;

@Getter
@Setter
public class TerminalImpl implements Terminal {
    private long ID;
    private long balance = 0L;
    private int attempts = 0;
    private boolean isAuthenticated = false;
    private Instant lockTime = null;

    private final TerminalServer server;
    private final PinValidator pinValidator;

    public TerminalImpl(long ID, TerminalServer serverTerminal){
        this.ID = ID;
        this.server = serverTerminal;
        this.pinValidator = new PinValidator(this.ID);

        setPin();

        TerminalServer.getAccountBase().put(this.ID, this);
    }

    @Override
    public long checkBalance(Long ID) throws AuthenticationException, AccountNotFoundException {
        if(!isAuthenticated){
            throw new AuthenticationException("Not Authenticated. Please enter the PIN code!");
        }

        return this.server.getBalance(ID);
    }

    @Override
    public void depositMoney(Long amount, Long ID) throws AuthenticationException, InvalidAmountException, AccountNotFoundException {
        if(!isAuthenticated){
            throw new AuthenticationException("Not Authenticated. Please enter the PIN code!");
        }

        if(amount % 100 != 0){
            throw new InvalidAmountException("Invalid amount. Enter a positive amount that is a multiple of 100.");
        }

        this.server.deposit(amount, ID);

    }

    @Override
    public void enterPin() throws AccountIsLockedException {
        if (isAccountLocked()) {
            throw new AccountIsLockedException("Account is locked. Time remaining: " + getLockTimeRemaining() + " seconds.");
        }


        int resultPin = PinValidator.pinReceiver();
        try {
            if (pinValidator.validatePIN(resultPin)) {
                this.isAuthenticated = true;
                this.attempts = 0;
                System.out.println("Authentication successful!");
            }
        } catch (InccorectPinException e) {
            this.attempts++;
            if (this.attempts >= 3) {
                lockAccount();
                throw new AccountIsLockedException("Account is locked due to 3 incorrect PIN attempts. Lock duration: 10 seconds.");
            }
            System.err.println("Wrong PIN, try again (" + (3 - attempts) + " attempts left).");
        }
    }

    private void lockAccount() {
        this.lockTime = Instant.now().plusSeconds(10); //
        this.isAuthenticated = false;
        this.attempts = 0;
    }

    private boolean isAccountLocked() {
        if (lockTime == null) {
            return false;
        }

        if (Instant.now().isAfter(lockTime)) {
            lockTime = null;
            return false;
        }
        return true;
    }

    private long getLockTimeRemaining() {
        if (lockTime == null) {
            return 0;
        }
        return Math.max(0, lockTime.getEpochSecond() - Instant.now().getEpochSecond());
    }

    @Override
    public void withdrawMoney(Long amount, Long ID) throws AuthenticationException, InvalidAmountException, AccountNotFoundException {
        if (!isAuthenticated) {
            throw new AuthenticationException("Not authenticated. Please enter your PIN.");
        }

        if (amount <= 0 || amount % 100 != 0) {
            throw new InvalidAmountException("Invalid amount. Enter a positive amount that is a multiple of 100.");
        }

        long currentBalance = this.server.getBalance(ID);
        if (currentBalance < amount) {
            throw new InvalidAmountException("Insufficient funds.");
        }

        this.server.withdraw(amount, ID);
        System.out.println("Withdrawal successful. New balance: " + this.server.getBalance(ID));
    }


    private void setPin() {
        System.out.println("Let's create a pin code! It must have 4 digits.");
        int resultPin = PinValidator.pinReceiver();
        System.out.println("Your PIN is successfully set to: " + resultPin);
        this.pinValidator.setPIN(resultPin);
    }

}
