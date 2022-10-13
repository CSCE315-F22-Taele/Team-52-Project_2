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

import org.postgresql.core.SqlCommand;

import edu.tamu.spinnstone.models.sql.Database;
import edu.tamu.spinnstone.models.sql.Table;

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
        columnNames = new ArrayList<>(Arrays.asList("order_id", "order_date", "order_total"));
        columnTypes = new ArrayList<>(Arrays.asList(ColumnType.LONG, ColumnType.DATE, ColumnType.MONEY));
        orderItems = new ArrayList<>();
        orderId = -1;
    }

    // region overrides

    @Override
    public ArrayList<Object> getColumnValues() {
        return new ArrayList<>(Arrays.asList(this.orderId, this.orderDate, this.orderTotal));
    }

    @Override
    public void setColumnValues(List<Object> values) {
        this.orderId = (long) values.get(0);
        this.orderDate = (Date) values.get(1);
        this.orderTotal = (BigDecimal) values.get(2);
    }

    public static Order create(Database db, Date date, BigDecimal total) throws SQLException {
        Order order = new Order(db);
        order.orderDate = date;
        order.orderTotal = total;
        order.orderId = order.insert();

        return order;
    }

    /*
     * 
     */
    public void calculateOrderTotal() {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.menuItem == null) {
                try {
                    orderItem.getMenuItem();
                } catch (Exception e) {
                    System.out.printf("unable to calculate order total: %s%n", e);

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
            for(Product product : orderItem.products) {
                product.decrementQuantity(1);
            }
        }
    }

    public boolean addOrderItem(OrderItem orderItem) {
        // adds a order item of the given menuitem type to the order and returns true if successful
        orderItems.add(orderItem);
        return true;
    }

    public boolean removeOrderItem(OrderItem orderItem) throws SQLException {
        // removes a order item of the given menuitem type from the order and returns true if successful
        // this should update the model to reflect the change
        orderItems.remove(orderItem);
        return true;
    }

    public boolean cancelOrder() throws SQLException {
        // cancels the order and returns true if successful
        orderItems.clear();
        return true;
    }

}
