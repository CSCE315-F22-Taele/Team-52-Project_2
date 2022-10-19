package edu.tamu.spinnstone.models;

import edu.tamu.spinnstone.models.sql.Database;
import edu.tamu.spinnstone.models.sql.Table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.ResultSet;
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

    /**
     * Create Order
     *
     * @param db
     * @param date
     * @param total
     * @return created order
     * @throws SQLException
     */
    public static Order create(Database db, Date date, BigDecimal total) throws SQLException {
        Order order = new Order(db);
        order.orderDate = date;
        order.orderTotal = total;
        order.orderId = order.insert();

        return order;
    }

    /**
     * Updates the local model orderTotal for currently referenced order from all menu_items currently in the database
     * Includes 6.25% food tax
     * Does not update database
     */
    public void calculateOrderTotal() {
        ResultSet rs;
        BigDecimal orderTotal;
        try {
            rs = database.query("select sum(menu_item_price) from order_item " + "join menu_item on menu_item.menu_item_id = order_item.menu_item_id " + "where order_item.order_id = " + orderId + ";");
            rs.next();
            subTotal = rs.getBigDecimal("sum");
            taxCharge = subTotal.multiply(new BigDecimal("0.0625")).setScale(2, RoundingMode.HALF_UP);
            orderTotal = subTotal.add(taxCharge);
        } catch (Exception e) {
            orderTotal = new BigDecimal("-1");
        }
        this.orderTotal = orderTotal;
    }

    // endregion

    /**
     * @throws SQLException
     */
    public void placeOrder() throws SQLException {
        if (orderDate == null) {
            orderDate = Date.valueOf(LocalDate.now());
        }
        // create the order
        orderId = insert();
        for (OrderItem orderItem : orderItems) {
            // create the order items (associated to this order)
            orderItem.orderId = orderId;
            orderItem.menuItemId = orderItem.menuItem.menuItemId;
            orderItem.orderItemId = orderItem.insert();

            // create the orderItem/product link
            orderItem.insertProducts();

            // update product inventory
            for (Product product : orderItem.products) {
                product.decrementQuantity(orderItem.quantity);
            }
        }

        // calculate the order total
        calculateOrderTotal();
    }

    /**
     * Adds an order item of the given menu item type to the order
     *
     * @param orderItem
     * @return true if successful, false otherwise
     */
    public boolean addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        return true;
    }

    /**
     * Removes an order item of the given menu item type from the order
     *
     * @param orderItem
     * @return true if successful, false otherwise
     * @throws SQLException
     */
    public boolean removeOrderItem(OrderItem orderItem) throws SQLException {
        // this should update the model to reflect the change
        orderItems.remove(orderItem);
        return true;
    }

    /**
     * Cancel the order
     *
     * @return true if successful, false otherwise
     * @throws SQLException
     */
    public boolean cancelOrder() throws SQLException {
        orderItems.clear();
        return true;
    }

}
