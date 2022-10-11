package edu.tamu.spinnstone.models;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.tamu.spinnstone.models.sql.Database;
import edu.tamu.spinnstone.models.sql.Table;

public class Order extends Table {
    public long orderId;
    public Date orderDate;
    public BigDecimal orderTotal;

    public List<OrderItem> orderItems;
    


    public Order(Database db) {
      super(db);
      tableName = "\"order\"";
      columnNames = new ArrayList<String>(Arrays.asList("order_id", "order_date", "order_total"));
      columnTypes = new ArrayList<ColumnType>(Arrays.asList(ColumnType.LONG, ColumnType.DATE, ColumnType.MONEY));
    }

    // region overrides

    @Override
    public ArrayList<Object> getColumnValues() {
      return new ArrayList<Object>(Arrays.asList(
        this.orderId,
        this.orderDate,
        this.orderTotal
      ));
    }

    @Override
    public void setColumnValues(List<Object> values) {
      this.orderId = (long) values.get(0);
      this.orderDate = (Date) values.get(1);
      this.orderTotal = (BigDecimal) values.get(2);
    }

    // endregion

    public boolean addOrderItem(MenuItem menuItem) throws SQLException {
      // adds a order item of the given menuitem type to the order and returns true if successful
      // this should update the model to reflect the change
      // this should update the order total locally
      throw new UnsupportedOperationException("Unimplemented");
    }

    public boolean removeOrderItem(MenuItem menuItem) throws SQLException {
      // removes a order item of the given menuitem type from the order and returns true if successful
      // this should update the model to reflect the change
      throw new UnsupportedOperationException("Unimplemented");
    }

    public boolean placeOrder() throws SQLException {
      // places the order and returns true if successful
      // these changes should be created or updated in the database
      throw new UnsupportedOperationException("Unimplemented");
    }

    public boolean cancelOrder() throws SQLException {
      // cancels the order and returns true if successful
      // these changes should be created or updated in the database
      throw new UnsupportedOperationException("Unimplemented");
    }






}
