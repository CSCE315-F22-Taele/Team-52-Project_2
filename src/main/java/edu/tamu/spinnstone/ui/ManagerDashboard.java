package edu.tamu.spinnstone.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import edu.tamu.spinnstone.models.MenuItem;
import edu.tamu.spinnstone.models.Product;
import edu.tamu.spinnstone.models.sql.Database;
import rx.subjects.PublishSubject;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ManagerDashboard {
    private JTable MenuItemsTable;
    private JPanel container;
    private JScrollPane DashboardTableContainer;
    private JButton addProductButton1;
    private JScrollPane RestockReportContainer;
    private JTable RestockReportTable;
    private JButton refreshButton;
    private JLabel restockLabel;
    private JScrollPane SalesReportTableContainer;
    private JTable SalesReportTable;
    private JTextField SalesReportEndDate;
    private JTextField SalesReportStartDate;
    private JButton SalesReportSubmitButton;
    private JLabel SalesReportError;
    private DataTable dataTable;
    private JDialog dialog;
    private final int RESTOCK_THRESHOLD;

    public ManagerDashboard() {
        RESTOCK_THRESHOLD = 500;
        $$$setupUI$$$();
        populateMenuItemsTable();
        generateRestockReport();
        restockLabel.setText("Restock Report");
        addProductButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                dialog = new JDialog(Actions.getFrame.getValue(), "New Menu Item", Dialog.ModalityType.DOCUMENT_MODAL);
                dialog.setContentPane(new AddProductDialog().$$$getRootComponent$$$());
                dialog.pack();
                Actions.activeDialog.onNext(dialog);
                dialog.setVisible(true);
            }
        });
        refreshButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                generateRestockReport();
            }
        });

        generateSalesReport("2020-09-11", "2023-10-31");
        SalesReportSubmitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                generateSalesReport(SalesReportStartDate.getText(), SalesReportEndDate.getText());
            }
        });
    }

    private void populateMenuItemsTable() {
        Database database = Actions.getDatabase.getValue();
        MenuItem menu_item = new MenuItem(database);
        ArrayList<String[]> dataToDisplay = new ArrayList<>();

        try {
            ResultSet product_data = database.query("select * from menu_item join menu_item_category on menu_item.menu_item_category_id = menu_item_category.menu_item_category_id");
            if (product_data == null) {
                return;
            }
            do {
                String[] dataRow = new String[3];

                dataRow[0] = product_data.getString("item_name");
                dataRow[1] = product_data.getString("menu_item_price");
                dataRow[2] = product_data.getString("menu_item_category_name");

                dataToDisplay.add(dataRow);

            } while (product_data.next());
        } catch (SQLException e) {
            System.out.println("SQL exception: " + e);
        }

        // Initializing the JTable
        String[] columnNames = {"Menu Item", "Price", "Category"};
        String[][] dataToDisplayArray = dataToDisplay.toArray(new String[dataToDisplay.size()][]);
        Integer[] editableCols = {1, 2};

        PublishSubject<TableModelEvent> changeListener = PublishSubject.create();

        dataTable = new DataTable(dataToDisplayArray, columnNames, editableCols, changeListener);

        changeListener.subscribe(event -> {
            Database db = Actions.getDatabase.getValue();
            String value = dataTable.getValueAt(event.getFirstRow(), event.getColumn()).toString();
            String itemName = dataTable.getValueAt(event.getFirstRow(), 0).toString();

            try {
                // this is fragile assuming the name is in the first column... beware
                db.update(MenuItem.TableName).set(new HashMap<String, Object>() {{
                    put(MenuItem.ColumnNames.MENU_ITEM_PRICE.toString(), new BigDecimal(value.substring(1))

                    );
                }}).where(MenuItem.ColumnNames.ITEM_NAME.toString(), itemName).execute();
            } catch (Exception e) {
                System.out.println(e);
            }
        });

        MenuItemsTable = new JTable(dataTable);

        DashboardTableContainer.setViewportView(MenuItemsTable);
    }

    private void generateRestockReport() {
        Database database = Actions.getDatabase.getValue();
        Product inventory = new Product(database);
        ArrayList<String[]> restockReport = new ArrayList<>();

        try {
            ResultSet product_data = inventory.getView();
            do {
                String[] dataRow = new String[2];

                dataRow[0] = product_data.getString("product_name");
                dataRow[1] = product_data.getString("quantity_in_stock");

                // add to restock report if quantity is below restock threshold
                if (Integer.parseInt(dataRow[1]) < RESTOCK_THRESHOLD) {
                    restockReport.add(dataRow);
                }
            } while (product_data.next());
        } catch (SQLException e) {
            System.out.println("SQL exception: " + e);
        }

        // Initializing the JTable
        String[] columnNames = {"Product Name", "Quantity in Stock"};
        String[][] dataToDisplayArray = restockReport.toArray(new String[restockReport.size()][]);
        RestockReportTable = new JTable(dataToDisplayArray, columnNames);

        RestockReportContainer.setViewportView(RestockReportTable);
    }

    public void generateSalesReport(String startDate, String endDate) {
        Database database = Actions.getDatabase.getValue();
        MenuItem menuItem = new MenuItem(database);
        ArrayList<String[]> salesReport = new ArrayList<>();
        SalesReportError.setText("");

        try {
            ResultSet sales_report = menuItem.getSalesReport(startDate, endDate);
            do {
                String[] dataRow = new String[2];

                dataRow[0] = sales_report.getString("item_name");
                dataRow[1] = sales_report.getString("sales");

                salesReport.add(dataRow);

            } while (sales_report.next());
        } catch (SQLException e) {
            SalesReportError.setText("Invalid Date");
            System.out.println("SQL exception: " + e);
        }

        String[] columnNames = {"Menu Item", "Sales"};
        String[][] dataToDisplayArray = salesReport.toArray(new String[salesReport.size()][]);
        SalesReportTable = new JTable(dataToDisplayArray, columnNames);
        SalesReportTableContainer.setViewportView(SalesReportTable);

    }

    private void createUIComponents() {
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        container = new JPanel();
        container.setLayout(new GridLayoutManager(9, 1, new Insets(8, 8, 8, 8), -1, -1));
        DashboardTableContainer = new JScrollPane();
        container.add(DashboardTableContainer, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        MenuItemsTable = new JTable();
        DashboardTableContainer.setViewportView(MenuItemsTable);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        container.add(panel1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        addProductButton1 = new JButton();
        addProductButton1.setBackground(new Color(-15066598));
        addProductButton1.setForeground(new Color(-1));
        addProductButton1.setText("Add Product");
        panel1.add(addProductButton1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        RestockReportContainer = new JScrollPane();
        container.add(RestockReportContainer, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        RestockReportTable = new JTable();
        RestockReportContainer.setViewportView(RestockReportTable);
        restockLabel = new JLabel();
        restockLabel.setText("Restock Report");
        container.add(restockLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Menu Items");
        container.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        container.add(panel2, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        refreshButton = new JButton();
        refreshButton.setBackground(new Color(-15066598));
        refreshButton.setForeground(new Color(-1));
        refreshButton.setText("Refresh");
        panel2.add(refreshButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel2.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Sales Report");
        container.add(label2, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SalesReportTableContainer = new JScrollPane();
        container.add(SalesReportTableContainer, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        SalesReportTable = new JTable();
        SalesReportTableContainer.setViewportView(SalesReportTable);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        container.add(panel3, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        SalesReportEndDate = new JTextField();
        SalesReportEndDate.setText("");
        panel3.add(SalesReportEndDate, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        SalesReportStartDate = new JTextField();
        panel3.add(SalesReportStartDate, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        SalesReportSubmitButton = new JButton();
        SalesReportSubmitButton.setBackground(new Color(-15066598));
        SalesReportSubmitButton.setForeground(new Color(-1));
        SalesReportSubmitButton.setHideActionText(true);
        SalesReportSubmitButton.setText("Submit");
        panel3.add(SalesReportSubmitButton, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel3.add(spacer3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 1, false));
        final JLabel label3 = new JLabel();
        label3.setText("Start Date (YYYY-MM-DD)");
        panel3.add(label3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("End Date (YYYY-MM-DD)");
        panel3.add(label4, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SalesReportError = new JLabel();
        SalesReportError.setText("Label");
        panel3.add(SalesReportError, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return container;
    }

}
