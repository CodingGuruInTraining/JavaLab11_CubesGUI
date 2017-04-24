//package com.mark;

/**
 * This class provides input methods to capture and parse
 * User's input values.
 */
public class Input {

    public static double checkDoubleInput(String input) {

        if (input != null) {

            while (true) {
                try {
                    double doubleInput = Double.parseDouble(input);
                    if (doubleInput >= 0) {
                        return doubleInput;
                    } else {
// TODO replace with a pop-up or something
                        System.out.println("Please enter a positive number.");
                    }
                } catch (NumberFormatException err) {
// TODO replace with a pop-up or something too
                    System.out.println("Please type a positive number.");
                }
            }
        }
        return 0;
    }
}