package edu.tamu.spinnstone.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import edu.tamu.spinnstone.models.Order;
import edu.tamu.spinnstone.models.OrderItem;
import edu.tamu.spinnstone.models.Product;
import edu.tamu.spinnstone.models.sql.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MenuOptions {
    private JPanel MenuOptionCards;
    private JPanel orderOptionsCard;
    private JPanel pizzaOptionsCard;

    private JPanel sauceButtonGroup;
    private JPanel cheeseButtonGroup;
    private JPanel crustButtonGroup;
    private JPanel meatButtonGroup1;
    private JPanel meatButtonGroup2;
    private JPanel veggieButtonGroup1;
    private JPanel veggieButtonGroup2;
    private JPanel veggieButtonGroup3;
    private JPanel drizzleButtonGroup;
    private JPanel orderOptionsCardRow1;
    private JPanel orderOptionsCardRow2;
    private JPanel orderOptionsCardRow3;
    private JPanel orderOptionsCardRow4;

    private ButtonGroup sauceGroup;
    private ButtonGroup cheeseGroup;
    private ButtonGroup crustGroup;

    private List<JToggleButton> pizzaButtons;
    private List<JToggleButton> sauceButtons;
    private List<JToggleButton> crustButtons;
    private List<JToggleButton> drizzleButtons;
    private List<JToggleButton> cheeseButtons;
    private List<JToggleButton> meatButtons;
    private List<JToggleButton> veggieButtons;
    private List<JButton> drinkButtons;

    private Database db;
    private HashMap<String, ArrayList<edu.tamu.spinnstone.models.MenuItem>> menuItemByCategory;
    private HashMap<String, ArrayList<Product>> productByType;
    private PizzaType pizzaType;

    // @formatter:off

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
        MenuOptionCards = new JPanel();
        MenuOptionCards.setLayout(new CardLayout(0, 0));
        MenuOptionCards.setBackground(new Color(-1644826));
        orderOptionsCard = new JPanel();
        orderOptionsCard.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        orderOptionsCard.setBackground(new Color(-1644826));
        MenuOptionCards.add(orderOptionsCard, "orderOptionsCard");
        orderOptionsCardRow1 = new JPanel();
        orderOptionsCardRow1.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
        orderOptionsCardRow1.setBackground(new Color(-1644826));
        orderOptionsCard.add(orderOptionsCardRow1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        orderOptionsCard.add(spacer1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        orderOptionsCardRow2 = new JPanel();
        orderOptionsCardRow2.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
        orderOptionsCardRow2.setBackground(new Color(-1644826));
        orderOptionsCard.add(orderOptionsCardRow2, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        orderOptionsCardRow3 = new JPanel();
        orderOptionsCardRow3.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
        orderOptionsCardRow3.setBackground(new Color(-1644826));
        orderOptionsCard.add(orderOptionsCardRow3, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        orderOptionsCardRow4 = new JPanel();
        orderOptionsCardRow4.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
        orderOptionsCardRow4.setBackground(new Color(-1644826));
        orderOptionsCard.add(orderOptionsCardRow4, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        pizzaOptionsCard = new JPanel();
        pizzaOptionsCard.setLayout(new GridLayoutManager(7, 1, new Insets(0, 0, 0, 0), -1, -1));
        pizzaOptionsCard.setBackground(new Color(-1644826));
        MenuOptionCards.add(pizzaOptionsCard, "pizzaOptionsCard");
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-1644826));
        pizzaOptionsCard.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new CardLayout(0, 0));
        panel2.setBackground(new Color(-1644826));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(80, -1), null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setForeground(new Color(-16777216));
        label1.setHorizontalAlignment(0);
        label1.setText("CRUST");
        panel2.add(label1, "Card1");
        crustButtonGroup = new JPanel();
        crustButtonGroup.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        crustButtonGroup.setBackground(new Color(-1644826));
        panel1.add(crustButtonGroup, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        pizzaOptionsCard.add(spacer2, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-1644826));
        pizzaOptionsCard.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new CardLayout(0, 0));
        panel4.setBackground(new Color(-1644826));
        panel3.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(80, -1), null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setForeground(new Color(-16777216));
        label2.setHorizontalAlignment(0);
        label2.setText("SAUCE");
        panel4.add(label2, "Card1");
        sauceButtonGroup = new JPanel();
        sauceButtonGroup.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        sauceButtonGroup.setBackground(new Color(-1644826));
        panel3.add(sauceButtonGroup, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel5.setBackground(new Color(-1644826));
        pizzaOptionsCard.add(panel5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new CardLayout(0, 0));
        panel6.setBackground(new Color(-1644826));
        panel5.add(panel6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(80, -1), null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setForeground(new Color(-16777216));
        label3.setHorizontalAlignment(0);
        label3.setText("CHEESE");
        panel6.add(label3, "Card1");
        cheeseButtonGroup = new JPanel();
        cheeseButtonGroup.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        cheeseButtonGroup.setBackground(new Color(-1644826));
        panel5.add(cheeseButtonGroup, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel7.setBackground(new Color(-1644826));
        pizzaOptionsCard.add(panel7, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new CardLayout(0, 0));
        panel8.setBackground(new Color(-1644826));
        panel7.add(panel8, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(80, -1), null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setForeground(new Color(-16777216));
        label4.setHorizontalAlignment(0);
        label4.setText("DRIZZLE");
        panel8.add(label4, "Card1");
        drizzleButtonGroup = new JPanel();
        drizzleButtonGroup.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        drizzleButtonGroup.setBackground(new Color(-1644826));
        panel7.add(drizzleButtonGroup, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel9.setBackground(new Color(-1644826));
        pizzaOptionsCard.add(panel9, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new CardLayout(0, 0));
        panel10.setBackground(new Color(-1644826));
        panel9.add(panel10, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(80, -1), null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setForeground(new Color(-16777216));
        label5.setHorizontalAlignment(0);
        label5.setText("MEAT");
        panel10.add(label5, "Card1");
        meatButtonGroup1 = new JPanel();
        meatButtonGroup1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        meatButtonGroup1.setBackground(new Color(-1644826));
        panel9.add(meatButtonGroup1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        meatButtonGroup2 = new JPanel();
        meatButtonGroup2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        meatButtonGroup2.setBackground(new Color(-1644826));
        panel9.add(meatButtonGroup2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel11.setBackground(new Color(-1644826));
        pizzaOptionsCard.add(panel11, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new CardLayout(0, 0));
        panel12.setBackground(new Color(-1644826));
        panel11.add(panel12, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(80, -1), null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setForeground(new Color(-16777216));
        label6.setHorizontalAlignment(0);
        label6.setText("VEGGIES");
        panel12.add(label6, "Card1");
        veggieButtonGroup1 = new JPanel();
        veggieButtonGroup1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        veggieButtonGroup1.setBackground(new Color(-1644826));
        panel11.add(veggieButtonGroup1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        veggieButtonGroup2 = new JPanel();
        veggieButtonGroup2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        veggieButtonGroup2.setBackground(new Color(-1644826));
        panel11.add(veggieButtonGroup2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        veggieButtonGroup3 = new JPanel();
        veggieButtonGroup3.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        veggieButtonGroup3.setBackground(new Color(-1644826));
        panel11.add(veggieButtonGroup3, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MenuOptionCards;
    }


    // @formatter:on
    private enum PizzaType {
        one_topping, cheese, BYO
    }

    public boolean checkToppingCount(OrderItem item) {
        int count = item.products.stream().map(p -> p.productTypeName).filter(name -> name.equals("Meat") || name.equals("Veggies")).collect(Collectors.toList()).size();

        if (count >= 4 && pizzaType == PizzaType.BYO) {
            return false;
        }
        if (count >= 1 && pizzaType == PizzaType.one_topping) {
            return false;
        }
        if (count >= 0 && pizzaType == PizzaType.cheese) {
            return false;
        }
        return true;
    }


    private void buildPizzaOptions() {
        crustButtonGroup.removeAll();
        crustGroup = new ButtonGroup();
        for (Product product : productByType.get(Product.ProductType.CRUST.toString())) {
            JToggleButton button = new JToggleButton(product.productName);
            Theme.button(button);
            crustButtons.add(button);
            crustButtonGroup.add(button);
            crustGroup.add(button);
            button.addItemListener(event -> {
                OrderItem item = Actions.activeOrderItem.getValue();
                Order order = Actions.getOrder.getValue();
                if (button.isSelected()) {
                    item.addProduct(product);
                } else {
                    item.removeProduct(product);
                }
                Actions.getOrder.onNext(order);
            });
        }
        sauceButtonGroup.removeAll();
        sauceGroup = new ButtonGroup();
        for (Product product : productByType.get(Product.ProductType.SAUCE.toString())) {
            JToggleButton button = new JToggleButton(product.productName);
            Theme.button(button);
            sauceButtons.add(button);
            sauceButtonGroup.add(button);
            sauceGroup.add(button);
            button.addItemListener(event -> {
                OrderItem item = Actions.activeOrderItem.getValue();
                Order order = Actions.getOrder.getValue();
                if (button.isSelected()) {
                    item.addProduct(product);
                } else {
                    item.removeProduct(product);
                }
                Actions.getOrder.onNext(order);
            });
        }
        cheeseButtonGroup.removeAll();
        cheeseGroup = new ButtonGroup();
        for (Product product : productByType.get(Product.ProductType.CHEESE.toString())) {
            JToggleButton button = new JToggleButton(product.productName);
            Theme.button(button);
            cheeseButtons.add(button);
            cheeseButtonGroup.add(button);
            cheeseGroup.add(button);
            button.addItemListener(event -> {
                OrderItem item = Actions.activeOrderItem.getValue();
                Order order = Actions.getOrder.getValue();
                if (button.isSelected()) {
                    item.addProduct(product);
                } else {
                    item.removeProduct(product);
                }
                Actions.getOrder.onNext(order);
            });
        }
        drizzleButtonGroup.removeAll();
        for (Product product : productByType.get(Product.ProductType.DRIZZLE.toString())) {
            JToggleButton button = new JToggleButton(product.productName);
            Theme.button(button);
            drizzleButtons.add(button);
            drizzleButtonGroup.add(button);
            button.addItemListener(event -> {
                OrderItem item = Actions.activeOrderItem.getValue();
                Order order = Actions.getOrder.getValue();
                if (button.isSelected()) {
                    item.addProduct(product);
                } else {
                    item.removeProduct(product);
                }
                Actions.getOrder.onNext(order);
            });
        }
        meatButtonGroup1.removeAll();
        meatButtonGroup2.removeAll();
        for (Product product : productByType.get(Product.ProductType.MEAT.toString())) {
            JToggleButton button = new JToggleButton(product.productName);
            Theme.button(button);
            crustButtons.add(button);
            if (meatButtonGroup1.getComponentCount() < 4) {
                meatButtonGroup1.add(button);
            } else {
                meatButtonGroup2.add(button);
            }
            button.addItemListener(event -> {
                OrderItem item = Actions.activeOrderItem.getValue();
                Order order = Actions.getOrder.getValue();
                if (button.isSelected() && !checkToppingCount(item)) {
                    button.setSelected(false);
                    return;
                }
                if (button.isSelected()) {
                    item.addProduct(product);
                } else {
                    item.removeProduct(product);
                }
                Actions.getOrder.onNext(order);
            });
        }
        veggieButtonGroup1.removeAll();
        veggieButtonGroup2.removeAll();
        veggieButtonGroup3.removeAll();
        for (Product product : productByType.get(Product.ProductType.VEGGIES.toString())) {
            JToggleButton button = new JToggleButton(product.productName);
            Theme.button(button);
            veggieButtons.add(button);
            if (veggieButtonGroup1.getComponentCount() < 4) {
                veggieButtonGroup1.add(button);
            } else if (veggieButtonGroup2.getComponentCount() < 4) {
                veggieButtonGroup2.add(button);
            } else {
                veggieButtonGroup3.add(button);
            }
            button.addItemListener(event -> {
                OrderItem item = Actions.activeOrderItem.getValue();
                Order order = Actions.getOrder.getValue();
                if (button.isSelected() && !checkToppingCount(item)) {
                    button.setSelected(false);
                    return;
                }
                if (button.isSelected()) {
                    item.addProduct(product);
                } else {
                    item.removeProduct(product);
                }
                Actions.getOrder.onNext(order);
            });
        }
    }

    private void setOptionCardListener(Actions.OptionCards optionCard) {
        String categoryName;
        switch (optionCard) {
            case PIZZA:
                categoryName = edu.tamu.spinnstone.models.MenuItem.Categories.PIZZA.toString();
                break;
            case DRINKS:
                categoryName = edu.tamu.spinnstone.models.MenuItem.Categories.BEVERAGE.toString();
                break;
            default:
                categoryName = edu.tamu.spinnstone.models.MenuItem.Categories.OTHER.toString();
        }

        // get the relevant menu items
        if (!menuItemByCategory.containsKey(categoryName)) {
            // take to blank page if no category name
            orderOptionsCardRow1.removeAll();
            orderOptionsCardRow2.removeAll();
            orderOptionsCardRow3.removeAll();
            orderOptionsCardRow4.removeAll();

            CardLayout cl = (CardLayout) (MenuOptionCards.getLayout());
            cl.show(MenuOptionCards, "orderOptionsCard");
            orderOptionsCard.revalidate();
            orderOptionsCard.repaint();
            return;
        }
        ArrayList<edu.tamu.spinnstone.models.MenuItem> menuItems = menuItemByCategory.get(categoryName);

        // generate the menu item buttons
        orderOptionsCardRow1.removeAll();
        orderOptionsCardRow2.removeAll();
        orderOptionsCardRow3.removeAll();
        orderOptionsCardRow4.removeAll();

        for (edu.tamu.spinnstone.models.MenuItem menuItem : menuItems) {
            JButton button = new JButton(menuItem.itemName);
            Theme.button(button);

            if (orderOptionsCardRow1.getComponentCount() < 4) {
                orderOptionsCardRow1.add(button);
            } else if (orderOptionsCardRow2.getComponentCount() < 4) {
                orderOptionsCardRow2.add(button);
            } else if (orderOptionsCardRow3.getComponentCount() < 4) {
                orderOptionsCardRow3.add(button);
            } else {
                orderOptionsCardRow4.add(button);
            }
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent event) {
                    // add menu item to order (as an order item)
                    addOrderItem(menuItem);

                    // if it's a pizza, head to the configuration screen
                    if (categoryName == edu.tamu.spinnstone.models.MenuItem.Categories.PIZZA.toString()) {
                        switch (menuItem.itemName) {
                            case "2-4 Topping Pizza":
                                pizzaType = PizzaType.BYO;
                                break;
                            case "Original Cheese Pizza":
                                pizzaType = PizzaType.cheese;
                                break;
                            case "1 Topping Pizza":
                                pizzaType = PizzaType.one_topping;
                                break;

                        }
                        CardLayout cl = (CardLayout) (MenuOptionCards.getLayout());
                        cl.show(MenuOptionCards, "pizzaOptionsCard");
                    }
                }
            });
        }

        CardLayout cl = (CardLayout) (MenuOptionCards.getLayout());
        cl.show(MenuOptionCards, "orderOptionsCard");
        orderOptionsCard.revalidate();
        orderOptionsCard.repaint();
    }

    private void addOrderItem(edu.tamu.spinnstone.models.MenuItem menuItem) {
        Order activeOrder = Actions.getOrder.getValue();
        if (activeOrder == null) {
            System.out.println("no active order");
            return;
        }

        // check if there is already a menuitem matching this one (excluding pizzas)

        if (menuItem.categoryName != "Pizza") {
            OrderItem matchingItem = activeOrder.orderItems.stream().filter(item -> item.menuItem.itemName.equals(menuItem.itemName)).findFirst().orElse(null);

            if (matchingItem != null) {
                // increase this order item quantity instead
                matchingItem.quantity++;
                Actions.getOrder.onNext(activeOrder);
                return;
            }
        }

        // create a new order item with the appropriate menu item link
        OrderItem orderItem = new OrderItem(db);
        orderItem.menuItemId = menuItem.menuItemId;
        orderItem.menuItem = menuItem;
        orderItem.quantity = 1;

        if (!menuItem.configurable) {
            try {
                // other items will have 1 associated product, add it to the menu item here
                PreparedStatement statement = db.connection.prepareStatement("select * from menu_item " + "join menu_item_product mip on menu_item.menu_item_id = mip.menu_item_menu_item_id " + "where menu_item.menu_item_id = ? " + "limit 1");
                statement.setLong(1, menuItem.menuItemId);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    Product product = new Product(db);
                    product.productId = rs.getLong("product_product_id");
                    product.sync();
                    orderItem.addProduct(product);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        activeOrder.orderItems.add(orderItem);
        Actions.activeOrderItem.onNext(orderItem);
        Actions.getOrder.onNext(activeOrder);

        pizzaButtons.clear();

        pizzaButtons.addAll(sauceButtons);
        pizzaButtons.addAll(crustButtons);
        pizzaButtons.addAll(drizzleButtons);
        pizzaButtons.addAll(cheeseButtons);
        pizzaButtons.addAll(meatButtons);
        pizzaButtons.addAll(veggieButtons);


        for (JToggleButton button : pizzaButtons) {
            button.setSelected(false);
        }

        sauceGroup.clearSelection();
        cheeseGroup.clearSelection();
        crustGroup.clearSelection();
    }

    public void buildItemMaps() {
        pizzaButtons = new ArrayList<>();
        sauceButtons = new ArrayList<>();
        crustButtons = new ArrayList<>();
        drizzleButtons = new ArrayList<>();
        cheeseButtons = new ArrayList<>();
        meatButtons = new ArrayList<>();
        veggieButtons = new ArrayList<>();
        menuItemByCategory = new HashMap<>();
        productByType = new HashMap<>();
        try {
            ResultSet rs = db.query("select * from menu_item join menu_item_category mic on menu_item.menu_item_category_id = mic.menu_item_category_id");
            do {
                edu.tamu.spinnstone.models.MenuItem menuItem = new edu.tamu.spinnstone.models.MenuItem(db);
                menuItem.updateFromResultSet(rs);
                menuItem.categoryName = rs.getString("menu_item_category_name");
                if (!menuItemByCategory.containsKey(menuItem.categoryName)) {
                    menuItemByCategory.put(menuItem.categoryName, new ArrayList<>());
                }
                menuItemByCategory.get(menuItem.categoryName).add(menuItem);
            } while (rs.next());
        } catch (Exception ex) {
            System.out.println("Error trying to get menu items: " + ex.getMessage());
            return;
        }

        // fetch products and group by type

        try {
            ResultSet rs = db.query("select * from product join product_type pt on product.product_type_id = pt.product_type_id");

            while (rs.next()) {
                Product product = new Product(db);
                product.updateFromResultSet(rs);
                product.productTypeName = rs.getString("product_type_name");
                if (!productByType.containsKey(product.productTypeName)) {
                    productByType.put(product.productTypeName, new ArrayList<>());
                }
                productByType.get(product.productTypeName).add(product);
            }
        } catch (Exception ex) {
            System.out.println("Error trying to get products: " + ex.getMessage());
            return;
        }


    }

    public MenuOptions() {
        sauceButtonGroup.removeAll();
        cheeseButtonGroup.removeAll();
        crustButtonGroup.removeAll();
        meatButtonGroup1.removeAll();
        meatButtonGroup2.removeAll();
        veggieButtonGroup1.removeAll();
        veggieButtonGroup2.removeAll();
        veggieButtonGroup3.removeAll();


        // initialize
        db = Actions.getDatabase.getValue();

        if (db == null) {
            System.out.println("database is null");
            return;
        }

        buildItemMaps();
        buildPizzaOptions();

        Actions.menuItemAdded.subscribe(a -> buildItemMaps());

        // listen for card change actions
        Actions.setOptionsCard.subscribe(card -> setOptionCardListener(card));


    }

}
