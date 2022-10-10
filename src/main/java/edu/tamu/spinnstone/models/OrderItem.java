package edu.tamu.spinnstone.models;

import java.awt.*;
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
    public MenuItem menuItem;


    public OrderItem(Database db) {
      super(db);
      tableName = "order_item";
      columnNames = new ArrayList<String>(Arrays.asList("order_item_id", "order_id", "menu_item_id"));
      columnTypes = new ArrayList<ColumnType>(Arrays.asList(ColumnType.LONG, ColumnType.LONG, ColumnType.LONG));
      products = new ArrayList<Product>();
    }

    // region overrides
    @Override
    public ArrayList<Object> getColumnValues() {
      return new ArrayList<Object>(Arrays.asList(
        this.orderItemId,
        this.orderId,
        this.menuItemId
      ));
    }

    @Override
    public void setColumnValues(List<Object> values) {
      this.orderItemId = (long) values.get(0);
      this.orderId = (long) values.get(1);
      this.menuItemId = (long) values.get(2);
    }
    // endregion

    //region static methods
    public static OrderItem create(Database db, long orderId, long menuItemId) throws SQLException {
      OrderItem orderItem = new OrderItem(db);
      orderItem.orderId = orderId;
      orderItem.menuItemId = menuItemId;
      orderItem.orderItemId = orderItem.insert();

      return orderItem;
    }
    //endregion

    public void getMenuItem() throws SQLException {
        MenuItem item = new MenuItem(database);
        boolean found = item.find(menuItemId);
        if(!found) {
            throw new SQLException("unable to find menu item");
        }

        this.menuItem = item;

    }


    public boolean addProduct(long productId) throws SQLException {
      // adds a product to the order item and returns true if successful
      throw new UnsupportedOperationException();
    }

    public boolean removeProduct(long productId) throws SQLException {
      // removes a product from the order item and returns true if successful
      throw new UnsupportedOperationException();
    }



}
