import javax.swing.*;
import javax.swing.table.TableColumn;

/**
 * Created by hl4350hb on 4/19/2017.
 */
public class CubesGUI extends JFrame {
    private JPanel rootPanel;
    private JTable botTable;
    private JButton addButton;
    private JButton updateButton;
    private JTextField botTextField;
    private JTextField timeTextField;

    CubesGUI() {
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

//        TableColumn col = botTable.getTableHeader().getColumnModel().getColumn(0);
        botTable.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("Bot Name");
        botTable.getTableHeader().getColumnModel().getColumn(1).setHeaderValue("Time");
    }
}




//https://coderanch.com/t/339603/java/JTable-setting-column-title