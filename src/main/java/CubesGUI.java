import javax.swing.*;

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

    CubesGUI() {
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        CubesDB dbManager = new CubesDB();
    }
}