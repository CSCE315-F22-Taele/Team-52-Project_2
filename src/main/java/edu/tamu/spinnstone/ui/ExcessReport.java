package edu.tamu.spinnstone.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import edu.tamu.spinnstone.models.Product;
import edu.tamu.spinnstone.models.sql.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class ExcessReport {
    private JPanel Container;
    private JTable ExcessTable;
    private JTextField StartDateExcess;
    private JButton ExcessSubmit;
    private JLabel ExcessLabel;
    private JScrollPane ExcessTableContainer;

    public ExcessReport() {
        ExcessSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
            }
        });
    }

    /*
     * @param
     * @param
     */
    private void generateExcessReport(String timeStampStart, String timeStampEnd) {
        Database database = Actions.getDatabase.getValue();
        Product product = new Product(database);

        //the time stamp ending date is the current date
        timeStampEnd = (LocalDate.now(ZoneId.systemDefault())).toString();

        ArrayList<String[]> excessReport = new ArrayList<>();
        ArrayList<String[]> inventoryOfProduct = new ArrayList<>();
        ArrayList<String[]> excessReportRemaining = new ArrayList<>();

        try {
            //This query tells how much of each product has been sold between a specific start and end date
            ResultSet productItems = product.getProductReport(timeStampStart, timeStampEnd);
            do {
                String[] dataRow = new String[2];

                dataRow[0] = productItems.getString("product_id");
                dataRow[1] = productItems.getString("?column?");

                excessReport.add(dataRow);

                //This holds product_id of each product
                ResultSet productId = product.totalInventoryOfProduct(Integer.parseInt(dataRow[0]));

                String[] productRow = new String[1];

                productRow[0] = productId.getString("product_id");

                inventoryOfProduct.add(productRow);
            }
            while (productItems.next());
        } catch (SQLException e) {
            //SalesReportError.setText("Invalid Date");
            System.out.println("SQL exception: " + e);
        }

//        for(int i = 0; i < excessReport.size(); ++i) {
//            //if the amount sold / total inventory of item < 10%, then add to table
//            double percent = Double(Integer.parseInt(excessReport.get(i))/inventoryOfProduct.get(i))
//            if(percent < .10) {
//                excessReportRemaining.add(excessReport.get(i));
//            }
//        }

        String[][] excessReportArray = excessReport.toArray(new String[excessReport.size()][]);
        String[][] inventoryArray = inventoryOfProduct.toArray(new String[inventoryOfProduct.size()][]);

        for (int i = 0; i < excessReport.size(); ++i) {
            for (int j = 0; j < 2; j++) {
                //if the amount sold / total inventory of item < 10%, then add to table
                //int division = Integer.parseInt(excessReport.get(i)[j]) / Integer.parseInt(inventoryOfProduct.get(i)[0]);
                double percent = Integer.parseInt(excessReport.get(i)[j]) / Integer.parseInt(inventoryOfProduct.get(i)[0]);
                if (percent < .10) {
                    excessReportRemaining.add(excessReport.get(i));
                }
            }
        }


        String[] columnNames = {"Product Sold Less Than 10%"};
        String[][] dataToDisplayArray = excessReportRemaining.toArray(new String[excessReportRemaining.size()][]);
        ExcessTable = new JTable(dataToDisplayArray, columnNames);
        ExcessTableContainer.setViewportView(ExcessTable);
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
        Container = new JPanel();
        Container.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        ExcessTableContainer = new JScrollPane();
        Container.add(ExcessTableContainer, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        ExcessTable = new JTable();
        ExcessTableContainer.setViewportView(ExcessTable);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        Container.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        ExcessLabel = new JLabel();
        ExcessLabel.setText("Excess Report");
        panel1.add(ExcessLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        Container.add(panel2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        ExcessSubmit = new JButton();
        ExcessSubmit.setBackground(new Color(-15066598));
        ExcessSubmit.setForeground(new Color(-1));
        ExcessSubmit.setText("Submit");
        panel3.add(ExcessSubmit, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel2.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel4, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        StartDateExcess = new JTextField();
        StartDateExcess.setText("");
        panel4.add(StartDateExcess, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Start Date (YYYY-MM-DD):");
        panel4.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return Container;
    }

    //generateExcessReport("2022-10-15", "2022-10-17");

}
