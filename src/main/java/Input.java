//package com.mark;

import javax.swing.*;

/**
 * This class provides input method to capture and parse
 * User's input values.
 */
public class Input {

    public static double checkDoubleInput(String input) {
        if (input != null) {
            try {
                double doubleInput = Double.parseDouble(input);
                if (doubleInput >= 0) {
                    return doubleInput;
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a positive number.");
                }
            } catch (NumberFormatException err) {
                JOptionPane.showMessageDialog(null, "Please type a positive number.");
            }
        }
        return 0;
    }
}