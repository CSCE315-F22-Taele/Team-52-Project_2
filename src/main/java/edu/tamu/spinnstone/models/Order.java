package edu.tamu.spinnstone.models;

import edu.tamu.spinnstone.models.sql.Database;
import edu.tamu.spinnstone.models.sql.Table;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Order extends Table {
    // db values
    public long orderId;
    public Date orderDate;
    public BigDecimal orderTotal;

    // local values
    public BigDecimal subTotal;
    public BigDecimal taxCharge;
    public List<OrderItem> orderItems;


    public Order(Database db) {
        super(db);
        tableName = "\"order\"";
        columnNames = new ArrayList<String>(Arrays.asList("order_id", "order_date", "order_total"));
        columnTypes = new ArrayList<ColumnType>(Arrays.asList(ColumnType.LONG, ColumnType.DATE, ColumnType.MONEY));
        orderItems = new ArrayList<OrderItem>();
        orderId = -1;
    }

    // region overrides

    @Override
    public ArrayList<Object> getColumnValues() {
        return new ArrayList<Object>(Arrays.asList(this.orderId, this.orderDate, this.orderTotal));
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
    }

    public static Order create(Database db, Date date, BigDecimal total) throws SQLException {
        Order order = new Order(db);
        order.orderDate = date;
        order.orderTotal = total;
        order.orderId = order.insert();

        return order;
    }


    public void calculateOrderTotal() {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.menuItem == null) {
                try {
                    orderItem.getMenuItem();
                } catch (Exception e) {
                    System.out.println(String.format("unable to calculate order total: %s", e));

                }
            }
        }
        subTotal = orderItems.stream().map(item -> item.menuItem.menuItemPrice).reduce(new BigDecimal(0), BigDecimal::add);
        taxCharge = subTotal.multiply(new BigDecimal(".0625"));
        orderTotal = subTotal.add(taxCharge);
    }

    // endregion

    public void placeOrder() throws SQLException {
        orderDate = Date.valueOf(LocalDate.now());
        // create the order
        orderId = insert();
        for (OrderItem orderItem : orderItems) {
            // create the order items (associated to this order)
            orderItem.orderId = orderId;
            orderItem.orderItemId = orderItem.insert();

            // create the orderItem/product link
            orderItem.insertProducts();

            // update product inventory
            for (Product product : orderItem.products) {
                product.decrementQuantity(1.0);
            }
        }
    }

    public void addOrderItem(OrderItem orderItem) {
        // adds an order item of the given menuitem type to the order and returns true if successful
        // this should update the model to reflect the change
        // this should update the order total locally
        orderItems.add(orderItem);
    }

    public boolean removeOrderItem(MenuItem menuItem) throws SQLException {
        // removes an order item of the given menuitem type from the order and returns true if successful
        // this should update the model to reflect the change
        throw new UnsupportedOperationException("Unimplemented");
    }

    public boolean cancelOrder() throws SQLException {
        // cancels the order and returns true if successful
        // these changes should be created or updated in the database
        throw new UnsupportedOperationException("Unimplemented");
    }


}
