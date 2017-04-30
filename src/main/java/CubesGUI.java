import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * This program provides a GUI to use a entry keeping database
 * on Rubik's cube solving times.
 */
public class CubesGUI extends JFrame {
    private JPanel rootPanel;
    private JButton addButton;
    private JButton updateButton;
    private JTextField botTextField;
    private JTextField timeTextField;
    private JList botList;
    private JButton deleteButton;
    private JButton confirmButton;
    private JLabel confirmLabel;
    private DefaultListModel<Bot> listModel;

    private byte option;

    // Constructor.
    CubesGUI() {
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Hides label and button for later use.
        confirmLabel.setVisible(false);
        confirmButton.setVisible(false);

        // Defines list model and selection option.
        listModel = new DefaultListModel<Bot>();
        botList.setModel(listModel);
        botList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Instantiates CubesDB object.
        CubesDB dbManager = new CubesDB();

        // Fills JList with current database entries.
        for (Bot b : dbManager.allBots) {
            listModel.addElement(b);
        }

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gets values from text fields and validates them.
                String newName = botTextField.getText();
                String newTimeStr = timeTextField.getText();
                if (!verifyName(newName)) {
                    return;
                }
                double newTime = verifyTime(newTimeStr);
                if (newTime == 0) {
                    return;
                }

                // Setting so validated data remains intact.
                dbManager.setTempName(newName);
                dbManager.setTempTime(newTime);
                option = 1;
                // Displays label and button.
                confirmLabel.setVisible(true);
                confirmButton.setVisible(true);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                option = 2;
                // Gets the Bot object of selected item and
                // fills in text fields with its data.
                Bot b = (Bot)botList.getSelectedValue();
                botTextField.setText(b.botName);
                timeTextField.setText(b.botTime + "");
                // Disables name field and displays label and button.
                botTextField.setEnabled(false);
                confirmLabel.setVisible(true);
                confirmButton.setVisible(true);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                option = 3;
//                JOptionPane.showConfirmDialog(rootPanel, "Click on the 'Confirm' " +
//                                "button if you are serious about deleting this entry.",
//                        "Confirm Deletion",
//                        JOptionPane.OK_OPTION);

                // Displays label and button.
                confirmLabel.setVisible(true);
                confirmButton.setVisible(true);
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Central manager button/method for actions.
                String newName;
                double newTime;
                // Gets selected Bot object from JList.
                Bot b = (Bot)botList.getSelectedValue();
                switch (option) {
                    case 1:     // Add
                        // Gets values stored in manager and sends to method
                        // for processing.
                        newName = dbManager.getTempName();
                        newTime = dbManager.getTempTime();
                        dbManager.makeChanges(1, newName, newTime);

                        // Adds new Bot object to LinkedList.
                        dbManager.allBots.add(new Bot(newName, newTime));
                        break;

                    case 2:     // Update
                        // Validates input.
                        newTime = verifyTime(timeTextField.getText());
                        if (newTime == 0) {
                            return;
                        }
                        // Sends value to method for processing.
                        dbManager.makeChanges(2, b.botName, newTime);
                        // Updates Bot's attribute.
                        b.botTime = newTime;
                        botTextField.setEnabled(true);
                        break;

                    case 3:     // Delete
                        // 0 is sent as time to fill parameter, but isn't used.
                        dbManager.makeChanges(3, b.botName, 0);
                        // Removes Bot from LinkedList
                        dbManager.allBots.remove(b);
                        break;
                }
                // Runs methods to clear text fields and refresh the JList.
                clearFields();
                refreshList(dbManager.allBots);
                option = 0;
                confirmLabel.setVisible(false);
                confirmButton.setVisible(false);
            }
        });
    }

    private boolean verifyName(String name) {
        // Checks name isn't blank.
        if (name.equals("") || name == null) {
            JOptionPane.showMessageDialog(rootPanel, "Please enter a name.",
                    "Need Name", JOptionPane.OK_OPTION);
            return false;
        }
        return true;
    }

    private double verifyTime(String timeStr) {
        // Checks time is a number.
        double time = Input.checkDoubleInput(timeStr);
        if (time == 0) {
            JOptionPane.showMessageDialog(rootPanel, "Please enter a time.",
                    "Need Time", JOptionPane.OK_OPTION);
            return 0;
        }
        return time;
    }

    private void refreshList(LinkedList<Bot> bots) {
        // Clears list content and re-adds it with the current data.
        listModel.clear();
        for (Bot b : bots) { listModel.addElement(b); }
    }

    private void clearFields() {
        // Clears text fields.
        botTextField.setText("");
        timeTextField.setText("");
    }

//    private void disableButtons(boolean x) {
//        addButton.setEnabled(!x);
//        updateButton.setEnabled(!x);
//        deleteButton.setEnabled(!x);
//    }
}