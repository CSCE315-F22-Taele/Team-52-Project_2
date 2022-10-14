package edu.tamu.spinnstone.models;

import edu.tamu.spinnstone.models.sql.Database;
import edu.tamu.spinnstone.models.sql.Table;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.tamu.spinnstone.models.sql.Database;
import edu.tamu.spinnstone.models.sql.Table;
import edu.tamu.spinnstone.models.sql.Query;

import org.postgresql.core.SqlCommand;

public class Product extends Table {
    //region Fields
    public long productId;
    public String productName;
    public int quantityInStock;
    public double conversionFactor;
    //endregion

    public Product(Database db) {
        super(db);
        this.tableName = "product";
        this.columnNames = Arrays.asList("product_id", "product_name", "quantity_in_stock", "conversion_factor");
        this.columnTypes = Arrays.asList(ColumnType.LONG, ColumnType.STRING, ColumnType.INT, ColumnType.DOUBLE);
    }

    // region overrides

    @Override
    public ArrayList<Object> getColumnValues() {
        return new ArrayList<>(Arrays.asList(
                this.productId,
                this.productName,
                this.quantityInStock,
                this.conversionFactor
        ));
    }

    @Override
    public void setColumnValues(List<Object> values) {
        this.productId = (long) values.get(0);
        this.productName = (String) values.get(1);
        this.quantityInStock = (int) values.get(2);
        this.conversionFactor = (double) values.get(3);
    }
    // endregion

    // region static methods

    // Create new product in table
    public static Product create(Database db, String productName, int quantityInStock, double conversion_factor) throws SQLException {
        Product product = new Product(db);
        product.productName = productName;
        product.quantityInStock = quantityInStock;
        product.conversionFactor = conversion_factor;
        product.productId = product.insert();

        return product;
    }

    // endregion

    // region instance methods

    // Returns true if the quantiy was updated successfully in table for current product, false otherwise
    public void updateQuantity(int quantity) throws SQLException {
        sync();
        quantityInStock = quantity;
        update();
    }

    public void decrementQuantity() throws SQLException {
        sync();
        if (quantityInStock > 0) {
            quantityInStock--;
        }
        else {
            throw new SQLException("Inventory is zero");
        }
        update();
    }

    // endregion

    public enum ColumnNames {
        PRODUCT_ID("product_id"),
        PRODUCT_NAME("product_name"),
        QUANTITY_IN_STOCK("quantity_in_stock");

        private String columnName;

        ColumnNames(String columnName) {
            this.columnName = columnName;
        }

        public String toString() {
            return this.columnName;
        }
    }

    public enum Name {
        FOUNTAIN_CUP("Fountain Cup"),
        BOTTLE_BEVERAGE("Bottle Beverage"),
        GATORADE("Gatorade"),
        CAULIFLOWER("Cauliflower"),
        STANDARD("Standard"),
        ALFREDO("Alfredo"),
        TRADITIONAL_RED("Traditional Red"),
        ZESTY_RED("Zesty Red"),
        HOUSE_BLEND("House Blend"),
        PARMESAN("Parmesan"),
        BBQ_SAUCE("BBQ Sauce"),
        OLIVE_OIL("Olive Oil"),
        OREGANO("Oregano"),
        RANCH("Ranch"),
        SRIRACHA("Sriracha"),
        DICED_HAM("Diced Ham"),
        ITALIAN_SAUSAGE("Italian Sausage"),
        MEATBALL("Meatball"),
        PEPPERONI("Pepperoni"),
        SALAMI("Salami"),
        SMOKED_CHICKEN("Smoked Chicken"),
        BANANA_PEPPERS("Banana Peppers"),
        BLACK_OLIVES("Black Olives"),
        GREEN_PEPPERS("Green Peppers"),
        JALAPENOS("Jalapenos"),
        MUSHROOMS("Mushrooms"),
        ONIONS("Onions"),
        PINEAPPLE("Pineapple"),
        ROASTED_GARLIC("Roasted Garlic"),
        SPINACH("Spinach"),
        TOMATOES("Tomatoes");

        private final String name;

        Name(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }
}
