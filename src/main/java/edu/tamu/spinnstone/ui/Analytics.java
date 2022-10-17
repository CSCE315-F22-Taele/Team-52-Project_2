package edu.tamu.spinnstone.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import edu.tamu.spinnstone.models.sql.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Analytics {
    private JPanel panel1;
    private JTable pairTable;
    private JTextField fromDateField;
    private JTextField toDateField;
    private JButton searchButton;
    private JScrollPane tableContainer;

    public Analytics() {
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                try {
                    java.sql.Date startTime = java.sql.Date.valueOf(fromDateField.getText());
                    java.sql.Date endTime = java.sql.Date.valueOf(toDateField.getText());
                    determinePairs(startTime, endTime);
                } catch (Exception ee) {
                    System.out.println(ee);
                    String[][] tableDate = {{"Invalid input. Please enter valid dates.", "-1"}};
                    displayTable(tableDate);
                }
            }
        });
    }

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
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(4, 2, new Insets(8, 8, 8, 8), -1, -1));
        tableContainer = new JScrollPane();
        panel1.add(tableContainer, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        pairTable = new JTable();
        tableContainer.setViewportView(pairTable);
        fromDateField = new JTextField();
        panel1.add(fromDateField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        toDateField = new JTextField();
        panel1.add(toDateField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        searchButton = new JButton();
        searchButton.setBackground(new Color(-15066598));
        searchButton.setForeground(new Color(-1));
        searchButton.setText("Search");
        panel1.add(searchButton, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("From (YYYY-MM-DD):");
        panel1.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("To (YYYY-MM-DD):");
        panel1.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

    private void displayTable(String[][] rows) {
        String[] labels = {"Menu Item Pair", "Occurrences"};
        pairTable = new JTable(rows, labels);
        tableContainer.setViewportView(pairTable);
    }

    private void determinePairs(java.sql.Date startDate, java.sql.Date endDate) {
        Database database = Actions.getDatabase.getValue();
        HashMap<Set<String>, Integer> pairOccurrences = new HashMap<>();

        try {
            // get orders between start date and end date (inclusive)
            ResultSet rs_orders = database.query("select order_id from \"order\" " +
                    "where (order_date <= \'" + endDate.toLocalDate() + "\' and order_date >= \'" + startDate.toLocalDate() + "\');");

            // display no result if no orders in given range
            if (rs_orders == null) {
                String[][] tableDate = {{"No result", "-1"}};
                displayTable(tableDate);
                return;
            }

            // loop through orders to record pairs
            do {
                String orderId = rs_orders.getString(1);
                ResultSet rs_order_items = database.query("select item_name from order_item " +
                        "join menu_item on menu_item.menu_item_id=order_item.menu_item_id " +
                        "where order_item.order_id = " + orderId + ";");

                // pair items in order and record
                ArrayList<String> items = new ArrayList<>();
                do {
                    String currentItem = rs_order_items.getString(1);
                    for (int i = 0; i < items.size(); i++) {
                        Set<String> itemPair = new HashSet<>(Arrays.asList(currentItem, items.get(i)));

                        // record pair
                        if (pairOccurrences.containsKey(itemPair)) {
                            pairOccurrences.put(itemPair, pairOccurrences.get(itemPair) + 1);
                        } else {
                            pairOccurrences.put(itemPair, 1);
                        }
                    }

                    items.add(currentItem);

                } while (rs_order_items.next());
            } while (rs_orders.next());

            // order pairs by occurrences
            PriorityQueue<ArrayList<String>> pairsOrdered = new PriorityQueue<>(pairOccurrences.size(), new pairComparator());
            Iterator<Set<String>> pairsIterator = pairOccurrences.keySet().iterator();
            while (pairsIterator.hasNext()) {
                Set<String> currentPair = pairsIterator.next();
                String pairString = currentPair.toString();
                pairString = pairString.substring(1, pairString.length() - 1);

                int pairOccurrencesVal = pairOccurrences.get(currentPair);

                ArrayList<String> pairAndVal = new ArrayList<>(Arrays.asList(pairString, String.valueOf(pairOccurrencesVal)));
                pairsOrdered.add(pairAndVal);
            }

            // convert data to String[][] and display
            ArrayList<String[]> intermediateData = new ArrayList<>();
            for (int i = 0; i < pairOccurrences.size(); i++) {
                String[] movingPair = new String[2];
                movingPair[0] = pairsOrdered.peek().get(0);
                movingPair[1] = pairsOrdered.peek().get(1);
                intermediateData.add(movingPair);
                pairsOrdered.remove();
            }
            String[][] tableData = intermediateData.toArray(new String[pairOccurrences.size()][]);
            displayTable(tableData);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e);
        }
    }
}
