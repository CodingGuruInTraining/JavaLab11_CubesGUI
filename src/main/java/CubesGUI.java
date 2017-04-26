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

// TODO add that list model stuff.
        listModel = new DefaultListModel<Bot>();
        botList.setModel(listModel);
        botList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        CubesDB dbManager = new CubesDB();

        for (Bot b : dbManager.allBots) {
// TODO add bot object to list model thing.
            listModel.addElement(b);
        }





        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = botTextField.getText();
                String newTimeStr = timeTextField.getText();
                while (!verifyName(newName)) {
                    return;
                }

                double newTime = verifyTime(newTimeStr);
                while (newTime == 0) {
                    return;
                }
//                if (newName == "" || newName == null) {
//// TODO display a message somehow.
//                    JOptionPane.showConfirmDialog(rootPanel, "Please enter a name.",
//                            "Need Name", JOptionPane.OK_OPTION);
//                    return;
//                }

//                double newTime = Input.checkDoubleInput(timeTextField.getText());
//                if (newTime == 0) {
//// TODO display a message somehow.
//                    JOptionPane.showConfirmDialog(rootPanel, "Please enter a time",
//                            "Need Time", JOptionPane.OK_OPTION);
//                    return;
//                }

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

                confirmButton.setVisible(true);
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName;
                double newTime;
                switch (option) {
                    case 1:     // Add
                        newName = dbManager.getTempName();
                        newTime = dbManager.getTempTime();

                        try {
                            dbManager.psInsert.setString(1, newName);
                            dbManager.psInsert.setDouble(2, newTime);
                            dbManager.psInsert.executeUpdate();
                        }
                        catch (SQLException err) {
                            System.out.println("There was an error adding to the database.");
                            err.printStackTrace();
                        }

                        dbManager.allBots.add(new Bot(newName, newTime));
                        refreshList(dbManager.allBots);

                        break;
                    case 2:     // Update
                        newTime = verifyTime(timeTextField.getText());
                        while (newTime == 0) {
                            return;
                        }

                        try {
                            dbManager.psUpdate.setDouble(1, newTime);
                            dbManager.psUpdate.setString(2, botTextField.getText());
                            dbManager.psUpdate.executeUpdate();
                        }
                        catch (SQLException err) {
                            err.printStackTrace();
                        }
                        botTextField.setEnabled(true);
                        break;
                    case 3:     // Delete

                        break;
                }

                clearFields();
                option = 0;
                confirmButton.setVisible(false);
            }
        });
    }

    private boolean verifyName(String name) {
        if (name == "" || name == null) {
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
// TODO code method and add to the event listeners
    }
}