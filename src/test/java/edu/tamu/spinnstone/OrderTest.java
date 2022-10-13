package edu.tamu.spinnstone;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import edu.tamu.spinnstone.models.MenuItem;
import edu.tamu.spinnstone.models.Order;
import edu.tamu.spinnstone.models.OrderItem;
import edu.tamu.spinnstone.models.Product;
import edu.tamu.spinnstone.models.Shipment;
import edu.tamu.spinnstone.models.sql.Database;
import javafx.scene.control.Menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.ResultSet;

public class OrderTest {
    Database db;
    Order order;
    OrderTest() throws SQLException {
        db = new Database();
        db.connect();
        order = new Order(db);
    }

    @Before
    public void sync() throws SQLException {
        order.sync();
    }

    @Test
    public void calcualateOrderTotal() throws SQLException {
        order.find(1);
        order.calculateOrderTotal();
        order.update();
        Assert.assertEquals(new BigDecimal("12.29"), order.orderTotal);
    }
  
}
