package edu.tamu.spinnstone.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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

    // region instance methods

    public Boolean updateQuantity(double quantity) throws SQLException {
      // returns true if the update was successful, false otherwise
      throw new UnsupportedOperationException("updateQuantity not implemented");
    }

}
