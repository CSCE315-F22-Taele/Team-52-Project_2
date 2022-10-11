package edu.tamu.spinnstone.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.tamu.spinnstone.models.sql.Database;
import edu.tamu.spinnstone.models.sql.Table;

public class Product extends Table {
    //region Fields
    public long productId;
    public String productName;
    public double quantityInStock;
    //endregion

    public Product(Database db) {
        super(db);
        this.tableName = "product";
        this.columnNames = Arrays.asList("product_id", "product_name", "quantity_in_stock");
        this.columnTypes = Arrays.asList(ColumnType.LONG, ColumnType.STRING, ColumnType.DOUBLE);
    }

    // region overrides

    @Override
    public ArrayList<Object> getColumnValues() {
      return new ArrayList<Object>(Arrays.asList(
        this.productId,
        this.productName,
        this.quantityInStock
      ));
    }

    @Override
    public void setColumnValues(List<Object> values) {
      this.productId = (long) values.get(0);
      this.productName = (String) values.get(1);
      this.quantityInStock = (double) values.get(2);
    }
    // endregion

    // region static methods

    public static Product create(Database db, String productName, double quantityInStock) throws SQLException {
      Product product = new Product(db);
      product.productName = productName;
      product.quantityInStock = quantityInStock;
      product.productId = product.insert();

      return product;
    }

    // endregion

    // region instance methods

    public Boolean updateQuantity(double quantity) throws SQLException {
      // returns true if the update was successful, false otherwise
      throw new UnsupportedOperationException("updateQuantity not implemented");
    }

    // endregion

    // region static declarations

    public static enum ColumnNames {
        PRODUCT_ID("product_id"),
        PRODUCT_NAME("product_name"),
        QUANTITY_IN_STOCK("quantity_in_stock");

        private String columnName;

        private ColumnNames(String columnName) {
            this.columnName = columnName;
        }

        public String toString() {
            return this.columnName;
        }
    }

    public static enum Name {
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

      private Name(String name) {
        this.name = name;
      }
      public String toString() {
        return this.name;
      }
    }
}
