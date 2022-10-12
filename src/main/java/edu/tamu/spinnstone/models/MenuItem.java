package edu.tamu.spinnstone.models;

import edu.tamu.spinnstone.models.sql.Database;
import edu.tamu.spinnstone.models.sql.Table;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuItem extends Table {
    public long menuItemId;
    public String itemName;
    public BigDecimal menuItemPrice;


    public MenuItem(Database db) {
        super(db);
        tableName = "menu_item";
        columnNames = new ArrayList<>(Arrays.asList("menu_item_id", "item_name", "menu_item_price"));
        columnTypes = new ArrayList<>(Arrays.asList(ColumnType.LONG, ColumnType.STRING, ColumnType.MONEY));
    }

    // region overrides

    @Override
    public ArrayList<Object> getColumnValues() {
        return new ArrayList<>(Arrays.asList(
                this.menuItemId,
                this.itemName,
                this.menuItemPrice
        ));
    }

    @Override
    public void setColumnValues(List<Object> values) {
        this.menuItemId = (long) values.get(0);
        this.itemName = (String) values.get(1);
        this.menuItemPrice = (BigDecimal) values.get(2);
    }

    // endregion

    // region static methods

    public static MenuItem create(Database db, String name, BigDecimal price) throws SQLException {
        MenuItem menuItem = new MenuItem(db);
        menuItem.itemName = name;
        menuItem.menuItemPrice = price;
        menuItem.menuItemId = menuItem.insert();

        return menuItem;
    }

    // endregion

    // region instance methods

    public boolean setPrice(BigDecimal price) throws SQLException {
        // sets the price of the menu item and returns true if successful
        throw new UnsupportedOperationException("Unimplemented");
    }

    // endregion

    // region static declarations

    public enum ColumnIndexes {
        MENU_ITEM_ID(0),
        ITEM_NAME(1),
        MENU_ITEM_PRICE(2);

        private int index;

        private ColumnIndexes(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }

    public enum ColumnNames {
        MENU_ITEM_ID("menu_item_id"),
        ITEM_NAME("item_name"),
        MENU_ITEM_PRICE("menu_item_price");

        private String name;

        ColumnNames(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    public enum ItemNames {
        ONE_TOPPING_PIZZA("1 Topping Pizza"),
        TWO_TO_FOUR_TOPPING_PIZZA("2-4 Topping Pizza"),
        ORIGINAL_CHEESE_PIZZA("Original Cheese Pizza"),
        BOTTLED_BEVERAGE("Bottled Beverage"),
        GATORADE("Gatorade"),
        FOUNTAIN_DRINK("Fountain Drink");

        private String name;

        ItemNames(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }

    }

    //endregion

}

