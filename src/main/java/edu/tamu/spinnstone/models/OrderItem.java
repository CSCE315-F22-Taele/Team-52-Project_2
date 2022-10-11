package edu.tamu.spinnstone.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.tamu.spinnstone.models.sql.Database;
import edu.tamu.spinnstone.models.sql.Table;

public class OrderItem extends Table {
  public long orderItemId;
  public long orderId;
  public long menuItemId;

  public List<Product> products;

  public OrderItem(Database db) {
    super(db);
    tableName = "order_item";
    columnNames = new ArrayList<String>(Arrays.asList("order_item_id", "order_id", "menu_item_id"));
    columnTypes = new ArrayList<ColumnType>(Arrays.asList(ColumnType.LONG, ColumnType.LONG, ColumnType.LONG));
  }

  // region overrides
  @Override
  public ArrayList<Object> getColumnValues() {
    return new ArrayList<Object>(Arrays.asList(
        this.orderItemId,
        this.orderId,
        this.menuItemId));
  }

  @Override
  public void setColumnValues(List<Object> values) {
    this.orderItemId = (long) values.get(0);
    this.orderId = (long) values.get(1);
    this.menuItemId = (long) values.get(2);
  }
  // endregion

  public boolean addProduct(long productId) throws SQLException {
    // adds a product to the order item and returns true if successful
    throw new UnsupportedOperationException();
  }

  public boolean removeProduct(long productId) throws SQLException {
    // removes a product from the order item and returns true if successful
    throw new UnsupportedOperationException();
  }

}
