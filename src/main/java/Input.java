//package com.mark;

import javax.swing.*;

/**
 * This class provides input method to capture and parse
 * User's input values.
 */
public class Input {

    public static double checkDoubleInput(String input) {

        if (input != null) {

//            while (true) {
                try {
                    double doubleInput = Double.parseDouble(input);
                    if (doubleInput >= 0) {
                        return doubleInput;
                    } else {
                        JOptionPane.showMessageDialog(null, "Please enter a positive number.");
//// TODO replace with a pop-up or something
//                        System.out.println("Please enter a positive number.");
                    }
                } catch (NumberFormatException err) {
// TODO replace with a pop-up or something too
                    JOptionPane.showMessageDialog(null, "Please type a positive number.");
//                    System.out.println("Please type a positive number.");
                }
            }
//        }
        return 0;
    }
}