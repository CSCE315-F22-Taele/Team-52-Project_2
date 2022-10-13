package edu.tamu.spinnstone.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

public class Shipments {
    private JTable ShipmentsTable;
    private JPanel panel1;

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(8, 8, 8, 8),
                -1, -1));
        panel1.add(ShipmentsTable, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH,
                GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK |
                GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK |
                GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 50),
                null, 0, false));

    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

    public void createUIComponents() {
        // TODO: place custom component creation code here

        // Frame initialization
        JFrame f = new JFrame();

        // Frame Title
        f.setTitle("JTable Example");

        // Data to be displayed in the JTable
        String[][] data = {
                {"Kundan Kumar Jha", "4031", "CSE"},
                {"Anand Jha", "6014", "IT"}
        };

        // Column Names
        String[] columnNames = {"Name", "Roll Number", "Department"};

        // Initializing the JTable
        ShipmentsTable = new JTable(data, columnNames);
        ShipmentsTable.setBounds(30, 40, 200, 300);

        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(ShipmentsTable);
        f.add(sp);
        // Frame Size
        //f.setSize(500, 200);
        // Frame Visible = true
        f.setVisible(true);
    }
}
