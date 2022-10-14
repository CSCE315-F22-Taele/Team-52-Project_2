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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.ResultSet;


public class MenuItemTest {

    Database db;
    MenuItem menuItem;
    MenuItemTest() throws SQLException {
        db = new Database();
        db.connect();
        menuItem = new MenuItem(db);
    }

    @Before
    public void sync() throws SQLException {
        menuItem.sync();
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
    void getView() throws SQLException {
        ResultSet rs = menuItem.getView();
        Assert.assertEquals(rs.getString("menu_item_id").toString(), "1");
        Assert.assertEquals(rs.getString("item_name").toString(), "one topping pizza");
    }

    @Test
    void setPrice() throws SQLException {
        menuItem.find(1);
        menuItem.setPrice(new BigDecimal("0.01"));
        Assert.assertEquals(menuItem.menuItemPrice, new BigDecimal("0.01"));
        menuItem.find(1);
        menuItem.setPrice(new BigDecimal("7.79"));
    }

    @Test
    void create() throws SQLException {
        menuItem = MenuItem.create(db, "MenuItemTest", new BigDecimal("0.01"));
        menuItem.update();
        ResultSet rs = db.query("SELECT * FROM menu_item WHERE menu_item.item_name = \'MenuItemTest\'");
        rs.next();
        Assert.assertEquals(rs.getBigDecimal("menu_item_price"), new BigDecimal("0.01"));
        db.query("DELETE FROM menu_item WHERE menu_item.item_name = \'MenuItemTest\';");
    }  
}
