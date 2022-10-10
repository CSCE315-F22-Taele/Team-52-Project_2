package edu.tamu.spinnstone.models;

import java.math.BigDecimal;
import java.sql.Date;
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
      orderItems = new ArrayList<OrderItem>();
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

    @Override
    public void update() throws SQLException {
      super.update();
      // get the id of the order
      for (OrderItem orderItem : orderItems) {
        // cheap way to check if it already exists
        // we could probably do this in sql
        if(!orderItem.sync()) {
          orderItem.insert();
        }
      }
    }

    public static Order create(Database db, Date date, BigDecimal total) throws SQLException {
      Order order = new Order(db);
      order.orderDate = date;
      order.orderTotal = total;
      order.orderId = order.insert();

      return order;
    }

    public void calculateOrderTotal() {
        for(OrderItem orderItem : orderItems) {
            if(orderItem.menuItem == null) {
                try {
                    orderItem.getMenuItem();
                } catch (Exception e) {
                    System.out.println(String.format("unable to calculate order total: %s", e));

                }
            }
        }
        orderTotal = orderItems.stream().map(item -> item.menuItem.menuItemPrice).reduce(new BigDecimal(0), BigDecimal::add);
    }

    // endregion

    public void addOrderItem(OrderItem orderItem) {
      // adds a order item of the given menuitem type to the order and returns true if successful
      // this should update the model to reflect the change
      // this should update the order total locally
        orderItems.add(orderItem);
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
