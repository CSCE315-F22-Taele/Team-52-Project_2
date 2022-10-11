package edu.tamu.spinnstone.models;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.tamu.spinnstone.models.sql.Database;
import edu.tamu.spinnstone.models.sql.Table;

public class OrderItem extends Table {
    public long orderItemId;
    public long orderId;
    public long menuItemId;

    public List<Product> products;
    public MenuItem menuItem;

    // strictly for drinks
    public int quantity;
    public boolean isDrink;


    public OrderItem(Database db) {
        super(db);
        tableName = "order_item";
        columnNames = new ArrayList<String>(Arrays.asList("order_item_id", "order_id", "menu_item_id"));
        columnTypes = new ArrayList<ColumnType>(Arrays.asList(ColumnType.LONG, ColumnType.LONG, ColumnType.LONG));
        products = new ArrayList<Product>();
        quantity = 1;
        orderItemId = -1;
        isDrink = false;
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

    @Override
    public void update() throws SQLException {
        if(menuItem != null) {
            menuItemId = menuItem.menuItemId;
        }
        // create if not exists order_item_product relation for each product
        super.update();
    }
    // endregion

    public void insertProducts() throws SQLException {
        // assume all products have not been inserted
        for(Product product : products) {
            // probably a way to do this in sql with a single statement
//            ResultSet existingRecord = database.select("*").from(Database.TableNames.ORDER_ITEM_PRODUCT.toString())
//                    .where(new HashMap<String, Object>() {{
//                        put("order_item_order_item_id", orderItemId);
//                        put("product_product_id", product.productId);
//                    }}).execute();

//            if(!existingRecord.next()) {
                database.insert(Database.TableNames.ORDER_ITEM_PRODUCT.toString())
                        .columns("order_item_order_item_id", "product_product_id")
                        .values(orderItemId, product.productId).execute();
//            }
        }
    }

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
        if (!found) {
            throw new SQLException("unable to find menu item");
        }

        this.menuItem = item;

    }


    public void addProduct(Product product) {
        // adds a product to the order item and returns true if successful
        products.add(product);
    }

    public void removeProductByName(Product.Name name) {
        // removes a product from the order item
        products.removeIf(product -> product.productName.equals(name.toString()));
    }

    public boolean removeProduct(long productId) throws SQLException {
        // removes a product from the order item and returns true if successful
        throw new UnsupportedOperationException();
    }


}
