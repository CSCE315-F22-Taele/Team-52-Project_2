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
    public int quantityInStock;
    public double conversion_factor;
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
      return new ArrayList<Object>(Arrays.asList(
        this.productId,
        this.productName,
        this.quantityInStock,
        this.conversion_factor
      ));
    }

    @Override
    public void setColumnValues(List<Object> values) {
      this.productId = (long) values.get(0);
      this.productName = (String) values.get(1);
      this.quantityInStock = (int) values.get(2);
      this.conversion_factor = (double) values.get(3);
    }
    // endregion

    // region instance methods

    public Boolean updateQuantity(int quantity) throws SQLException {
      quantityInStock = quantity;
      update();
      return true;
    }

}
