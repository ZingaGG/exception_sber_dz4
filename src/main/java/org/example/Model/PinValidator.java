package org.example.Model;

import lombok.Setter;
import org.example.Exceptions.InccorectPinException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PinValidator {
    private long ID;

    @Setter
    private int PIN;

    public PinValidator(long id) {
        this.ID = id;
    }

    public boolean validatePIN(int PIN) throws InccorectPinException {
        if(this.PIN == PIN){
            return true;
        }

        throw new InccorectPinException("Wrong PIN");
    }

    public static int pinReceiver(){
        Scanner scanner = new Scanner(System.in);

        int number = 0;
        int currentPosition = 0;

        while (currentPosition < 4) {
            System.out.print("Enter a digit for position " + (currentPosition + 1) + ": ");
            try {
                int digit = scanner.nextInt();

                if (digit < 0 || digit > 9) {
                    throw new InputMismatchException("Input is not a single digit.");
                }

                number = number * 10 + digit;
                currentPosition++;
            } catch (InputMismatchException e) {
                System.err.println("Invalid input! Please enter a single digit (0-9).");
                scanner.nextLine();
            }
        }

        int resultPin = number;

        return resultPin;
    }
}
