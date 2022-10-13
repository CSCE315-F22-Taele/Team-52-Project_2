package edu.tamu.spinnstone.ui;

import edu.tamu.spinnstone.models.Product;
import edu.tamu.spinnstone.models.sql.Database;
import edu.tamu.spinnstone.models.Shipment;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

public class Shipments {
    private JTable ShipmentsTable;
    private JPanel panel1;
    private JScrollPane ShipmentsTableContainer;

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
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(8, 8, 8, 8), -1, -1));
        ShipmentsTableContainer = new JScrollPane();
        panel1.add(ShipmentsTableContainer, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        ShipmentsTableContainer.setViewportView(ShipmentsTable);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

    public void createUIComponents() {
        // TODO: place custom component creation code here

        Database database = Actions.getDatabase.getValue();
        Shipment shipments = new Shipment(database);

        //System.out.println(shipments.getColumnValues());

        // Data to be displayed in the JTable
        String[][] data = {
                {"Kundan Kumar Jha", "4031", "CSE"},
                {"Anand Jha", "6014", "IT"}
        };

        // Column Names
        String[] columnNames = {"Name", "Roll Number", "Department"};

        // Initializing the JTable
        ShipmentsTable = new JTable(data, columnNames);
    }
}
