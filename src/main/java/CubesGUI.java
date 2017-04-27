import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by hl4350hb on 4/19/2017.
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
    private DefaultListModel<Bot> listModel;

    private byte option;

    CubesGUI() {
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        confirmButton.setVisible(false);

        listModel = new DefaultListModel<Bot>();
        botList.setModel(listModel);
        botList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        CubesDB dbManager = new CubesDB();

        for (Bot b : dbManager.allBots) {
            listModel.addElement(b);
        }

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = botTextField.getText();
                String newTimeStr = timeTextField.getText();
                if (!verifyName(newName)) {
                    return;
                }

                double newTime = verifyTime(newTimeStr);
                if (newTime == 0) {
                    return;
                }

//                // Setting so validated data remains intact.
                dbManager.setTempName(newName);
                dbManager.setTempTime(newTime);
                option = 1;

                confirmButton.setVisible(true);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                option = 2;
                Bot b = (Bot)botList.getSelectedValue();
                botTextField.setText(b.botName);
                timeTextField.setText(b.botTime + "");
                botTextField.setEnabled(false);
                confirmButton.setVisible(true);

            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                option = 3;
                JOptionPane.showConfirmDialog(rootPanel, "Click on the 'Confirm' " +
                                "button if you are serious about deleting this entry.",
                        "Confirm Deletion",
                        JOptionPane.OK_OPTION);
                confirmButton.setVisible(true);

            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName;
                double newTime;
                Bot b = (Bot)botList.getSelectedValue();
                switch (option) {
                    case 1:     // Add
                        newName = dbManager.getTempName();
                        newTime = dbManager.getTempTime();
                        dbManager.makeChanges(1, newName, newTime);

                        dbManager.allBots.add(new Bot(newName, newTime));

                        break;
                    case 2:     // Update
                        newTime = verifyTime(timeTextField.getText());
                        if (newTime == 0) {
                            return;
                        }
                        dbManager.makeChanges(2, b.botName, newTime);

                        b.botTime = newTime;
                        botTextField.setEnabled(true);
                        break;
                    case 3:     // Delete

                        dbManager.makeChanges(3, b.botName, 0);
                        dbManager.allBots.remove(b);

                        break;
                }
// TODO remove object from linkedlist where applicable
                clearFields();
                refreshList(dbManager.allBots);
                option = 0;
                confirmButton.setVisible(false);
            }
        });
    }

    private boolean verifyName(String name) {
        if (name.equals("") || name == null) {
            JOptionPane.showConfirmDialog(rootPanel, "Please enter a name.",
                    "Need Name", JOptionPane.OK_OPTION);
            return false;
        }
        return true;
    }

    private double verifyTime(String timeStr) {
        double time = Input.checkDoubleInput(timeStr);
        if (time == 0) {
            JOptionPane.showConfirmDialog(rootPanel, "Please enter a time",
                    "Need Time", JOptionPane.OK_OPTION);
            return 0;
        }
        return time;
    }

    private void refreshList(LinkedList<Bot> bots) {
        listModel.clear();
        for (Bot b : bots) { listModel.addElement(b); }
    }

    private void clearFields() {
        botTextField.setText("");
        timeTextField.setText("");
    }

    private void disableButtons(boolean x) {
        addButton.setEnabled(!x);
        updateButton.setEnabled(!x);
        deleteButton.setEnabled(!x);
// TODO code method and add to the event listeners
    }
}