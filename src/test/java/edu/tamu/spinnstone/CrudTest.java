package edu.tamu.spinnstone;

import org.junit.jupiter.api.Test;

import edu.tamu.spinnstone.models.MenuItem;
import edu.tamu.spinnstone.models.Order;
import edu.tamu.spinnstone.models.OrderItem;
import edu.tamu.spinnstone.models.Product;
import edu.tamu.spinnstone.models.Shipment;
import edu.tamu.spinnstone.models.sql.Database;
import edu.tamu.spinnstone.models.sql.Table;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

/**
 * Unit test for simple App.
 */
class CrudTest {
    /**
     * Rigorous Test.
     */
    Database db;
    CrudTest() throws SQLException{
        db = new Database();
        db.connect();
    }
    @Test
    void productCrud() throws SQLException {
      // create 
      Product p = new Product(db);
      p.productName = "Test Product";
      p.quantityInStock = 10;
      p.productId = p.insert();

      assertNotNull(p.productId);

      // read
      Product p2 = new Product(db);
      p2.find(p.productId);

      assertEquals(p.productName, p2.productName);
      assertEquals(p.quantityInStock, p2.quantityInStock);

      p2.productName = "Test Product 2";

      // update
      p2.update();

      Product p3 = new Product(db);
      p3.find(p.productId);

      assertEquals(p2.productName, p3.productName);

      // delete
      p3.delete();

      Product p4 = new Product(db);
      boolean found = p4.find(p.productId);
      assertFalse(found);

    }
    @Test
    void shipmentCrud() throws SQLException {
      // create 
      Shipment s = new Shipment(db);
      s.fulfilled = false;
      s.shipmentDate = new Date(2022, 1, 1);
      s.shipmentId = s.insert();
      

      assertNotNull(s.shipmentId);

      // read
      Shipment s2 = new Shipment(db);
      s2.find(s.shipmentId);

      assertEquals(s.shipmentId, s2.shipmentId);

      s2.fulfilled = true;

      // update
      s2.update();

      Shipment s3 = new Shipment(db);
      s3.find(s.shipmentId);

      assertEquals(s2.shipmentId, s3.shipmentId);

      // delete
      s3.delete();

      Shipment s4 = new Shipment(db);
      boolean found = s4.find(s.shipmentId);
      assertFalse(found);

    }

    @Test
    void menuItemCrud() throws SQLException {
      // create 
      MenuItem m = new MenuItem(db);
      m.itemName = "Test Menu Item";
      m.menuItemPrice = new BigDecimal("10.00");
      m.menuItemId = m.insert();

      assertNotNull(m.menuItemId);

      // read
      MenuItem m2 = new MenuItem(db);
      m2.find(m.menuItemId);

      assertEquals(m.itemName, m2.itemName);
      assertEquals(m.menuItemPrice, m2.menuItemPrice);

      m2.itemName = "Test Menu Item 2";

      // update
      m2.update();

      MenuItem m3 = new MenuItem(db);
      m3.find(m.menuItemId);

      assertEquals(m2.itemName, m3.itemName);

      // delete
      m3.delete();

      MenuItem m4 = new MenuItem(db);
      boolean found = m4.find(m.menuItemId);
      assertFalse(found);

    }

    @Test
    void orderCrud() throws SQLException {
      // create 
      Order o = new Order(db);
      o.orderDate = new Date(2022, 1, 1);
      o.orderTotal = new BigDecimal("10.00");
      o.orderId = o.insert();

      assertNotNull(o.orderId);

      // read
      Order o2 = new Order(db);
      o2.find(o.orderId);

      assertEquals(o.orderId, o2.orderId);
      assertEquals(o.orderDate, o2.orderDate);
      assertEquals(o.orderTotal, o2.orderTotal);

      o2.orderDate = new Date(2022, 1, 2);

      // update
      o2.update();

      Order o3 = new Order(db);
      o3.find(o.orderId);

      assertEquals(o2.orderDate, o3.orderDate);

      // delete
      o3.delete();

      Order o4 = new Order(db);
      boolean found = o4.find(o.orderId);
      assertFalse(found);


    }

    @Test
    void orderItemCrud() throws SQLException {
      // create 
      OrderItem o = new OrderItem(db);
      // could fail if this id doesn't exist.. not great testing strategy
      o.orderId = 1;
      o.menuItemId = 1;
      o.orderItemId = o.insert();

      assertNotNull(o.orderItemId);

      // read
      OrderItem o2 = new OrderItem(db);
      o2.find(o.orderItemId);

      assertEquals(o.orderItemId, o2.orderItemId);
      assertEquals(o.menuItemId, o2.menuItemId);
      assertEquals(o.orderId, o2.orderId);


      o2.orderId = 2;

      // update
      o2.update();

      OrderItem o3 = new OrderItem(db);
      o3.find(o.orderItemId);

      assertEquals(o2.orderId, o3.orderId);

      // delete
      o3.delete();

      OrderItem o4 = new OrderItem(db);
      boolean found = o4.find(o.orderItemId);
      assertFalse(found);

    }
}