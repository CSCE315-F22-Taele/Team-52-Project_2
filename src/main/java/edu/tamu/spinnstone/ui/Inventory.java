package edu.tamu.spinnstone.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import edu.tamu.spinnstone.models.Product;
import edu.tamu.spinnstone.models.sql.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private JTable InventoryTable;
    private JPanel panel1;
    private JScrollPane InventoryTableContainer;
    private JButton refreshButton;
    private JButton commitChangeSButton;

    public Inventory() {
        $$$setupUI$$$();
        populateTable();
        refreshButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                populateTable();
            }
        });
        commitChangeSButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                Database database = Actions.getDatabase.getValue();
                Product inventory = new Product(database);

                // get product values from gui
                HashMap<String, String> productUpdates = new HashMap<String, String>();
                for (int i = 0; i < InventoryTable.getRowCount(); i++) {
                    productUpdates.put(String.valueOf(InventoryTable.getValueAt(i, 0)), String.valueOf(InventoryTable.getValueAt(i, 1)));
                }

                // TODO: Undo unit conversion on product values
                // System.out.println(productUpdates);

                try {
                    ResultSet product_data = inventory.getView();
                    do {
                        // update each row with new quantity value
                        String productName = product_data.getString("product_name");
                        String updateValue = productUpdates.get(productName);
                        if (!updateValue.isEmpty()) {
                            product_data.updateInt("quantity_in_stock", Integer.valueOf(updateValue));
                            product_data.updateRow();
                        }
                    } while (product_data.next());
                } catch (SQLException ee) {
                    System.out.println("SQL exception: " + ee);
                }

            }
        });
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
        panel1.setLayout(new GridLayoutManager(2, 2, new Insets(8, 8, 8, 8), -1, -1));
        InventoryTableContainer = new JScrollPane();
        panel1.add(InventoryTableContainer, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        InventoryTable = new JTable();
        InventoryTableContainer.setViewportView(InventoryTable);
        refreshButton = new JButton();
        refreshButton.setBackground(new Color(-15066598));
        refreshButton.setForeground(new Color(-1));
        refreshButton.setText("Refresh");
        panel1.add(refreshButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        commitChangeSButton = new JButton();
        commitChangeSButton.setBackground(new Color(-15066598));
        commitChangeSButton.setForeground(new Color(-1));
        commitChangeSButton.setText("Commit Change(s)");
        panel1.add(commitChangeSButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

    private void populateTable() {
        Database database = Actions.getDatabase.getValue();
        Product inventory = new Product(database);
        ArrayList<String[]> dataToDisplay = new ArrayList<>();

        try {
            ResultSet product_data = inventory.getView();
            do {
                String[] dataRow = new String[2];

                dataRow[0] = product_data.getString("product_name");
                dataRow[1] = product_data.getString("quantity_in_stock");

                dataToDisplay.add(dataRow);

            } while (product_data.next());
        } catch (SQLException e) {
            System.out.println("SQL exception: " + e);
        }

        // Initializing the JTable
        String[] columnNames = {"Product Name", "Quantity in Stock"};
        String[][] dataToDisplayArray = dataToDisplay.toArray(new String[dataToDisplay.size()][]);
        InventoryTable = new JTable(dataToDisplayArray, columnNames);

        InventoryTableContainer.setViewportView(InventoryTable);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        panel1 = new JPanel();
    }
}
