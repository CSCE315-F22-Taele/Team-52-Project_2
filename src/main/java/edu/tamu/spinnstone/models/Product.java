package edu.tamu.spinnstone.models;

import edu.tamu.spinnstone.models.sql.Database;
import edu.tamu.spinnstone.models.sql.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Product extends Table {
    // region Fields
    public long productId;
    public String productName;
    public int quantityInStock;
    public double conversionFactor;
    public long productTypeId;
    public static String TableName = "product";

    public String productTypeName;
    // endregion


    public Product(Database db) {
        super(db);
        this.tableName = "product";
        this.columnNames = Arrays.asList("product_id", "product_name", "quantity_in_stock", "conversion_factor", "product_type_id");
        this.columnTypes = Arrays.asList(ColumnType.LONG, ColumnType.STRING, ColumnType.INT, ColumnType.DOUBLE, ColumnType.LONG);
        this.productTypeName = "";
    }

    // region overrides

    @Override
    public ArrayList<Object> getColumnValues() {
        return new ArrayList<>(Arrays.asList(this.productId, this.productName, this.quantityInStock, this.conversionFactor, this.productTypeId));
    }

    @Override
    public void setColumnValues(List<Object> values) {
        this.productId = (long) values.get(0);
        this.productName = (String) values.get(1);
        this.quantityInStock = (int) values.get(2);
        this.conversionFactor = (double) values.get(3);
        this.productTypeId = (long) values.get(4);
    }
    // endregion

    // region static methods

    /**
     * Create new product in table
     *
     * @param db
     * @param productName
     * @param quantityInStock
     * @param conversion_factor
     * @param productTypeId
     * @return
     * @throws SQLException
     */
    public static Product create(Database db, String productName, int quantityInStock, double conversion_factor, long productTypeId) throws SQLException {
        Product product = new Product(db);
        product.productName = productName;
        product.quantityInStock = quantityInStock;
        product.conversionFactor = conversion_factor;
        product.productTypeId = productTypeId;
        product.productId = product.insert();

        return product;
    }

    // endregion

    // region instance methods

    /**
     * Update quantity in table for current product
     *
     * @param quantity new quantity in stock
     * @throws SQLException
     */
    public void updateQuantity(int quantity) throws SQLException {
        sync();
        quantityInStock = quantity;
        update();
    }

    /**
     * Decrements quantity in stock for current product
     *
     * @param by amount to decrement quantity by
     * @throws SQLException
     */
    public void decrementQuantity(double by) throws SQLException {
        database.update(tableName).set(String.format("%s = %s - %.3f", ColumnNames.QUANTITY_IN_STOCK.toString(), ColumnNames.QUANTITY_IN_STOCK.toString(), by)).where(String.format("%s = %s AND %s > 0", ColumnNames.PRODUCT_ID.toString(), prepareValue(productId), ColumnNames.QUANTITY_IN_STOCK.toString())).execute();
    }

    // endregion

    public enum ColumnNames {
        PRODUCT_ID("product_id"), PRODUCT_NAME("product_name"), QUANTITY_IN_STOCK("quantity_in_stock"), CONVERSION_FACTOR("conversion_factor"), PRODUCT_TYPE_ID("product_type_id");

        private String columnName;

        ColumnNames(String columnName) {
            this.columnName = columnName;
        }

        public String toString() {
            return this.columnName;
        }
    }

    public enum Name {
        FOUNTAIN_CUP("Fountain Cup"), BOTTLE_BEVERAGE("Bottle Beverage"), GATORADE("Gatorade"), CAULIFLOWER("Cauliflower"), STANDARD("Standard"), ALFREDO("Alfredo"), TRADITIONAL_RED("Traditional Red"), ZESTY_RED("Zesty Red"), HOUSE_BLEND("House Blend"), PARMESAN("Parmesan"), BBQ_SAUCE("BBQ Sauce"), OLIVE_OIL("Olive Oil"), OREGANO("Oregano"), RANCH("Ranch"), SRIRACHA("Sriracha"), DICED_HAM("Diced Ham"), ITALIAN_SAUSAGE("Italian Sausage"), MEATBALL("Meatball"), PEPPERONI("Pepperoni"), SALAMI("Salami"), SMOKED_CHICKEN("Smoked Chicken"), BANANA_PEPPERS("Banana Peppers"), BLACK_OLIVES("Black Olives"), GREEN_PEPPERS("Green Peppers"), JALAPENOS("Jalapenos"), MUSHROOMS("Mushrooms"), ONIONS("Onions"), PINEAPPLE("Pineapple"), ROASTED_GARLIC("Roasted Garlic"), SPINACH("Spinach"), TOMATOES("Tomatoes");

        private final String name;

        Name(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }

    public static enum ProductType {
        DRINK("Drink"), DRIZZLE("Drizzle"), SAUCE("Sauce"), CHEESE("Cheese"), MEAT("Meat"), VEGGIES("Veggies"), CRUST("Crust"), OTHER("Other");

        private final String name;

        ProductType(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }

    /**
     * Query to get how much of each product has been sold between a specific start and end date
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws SQLException
     */
    public ResultSet getProductsSold(String startDate, String endDate) throws SQLException {
        return database.query("select p.quantity_in_stock, p.product_name, round(sum(p.product_id) / p.product_id) sold from \"order\" " + "join order_item oi on \"order\".order_id = oi.order_id " + "join order_item_product oip on oi.order_item_id = oip.order_item_order_item_id " + "join product p on oip.product_product_id = p.product_id " + "where order_date between " + "\'" + startDate + "\'" + " and " + "\'" + endDate + "\' " + "group by p.product_id");
    }
}
