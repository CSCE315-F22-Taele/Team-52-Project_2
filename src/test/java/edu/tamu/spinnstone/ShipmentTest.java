package edu.tamu.spinnstone;

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

public class ShipmentTest {
    Database db;
    Shipment shipment;
    ShipmentTest() throws SQLException{
        db = new Database();
        db.connect();
        shipment = new Shipment(db);
    }
    @Test
    void addRemoveUpdateProduct() throws SQLException{

        Product product = new Product(db);
        product.find(1);


        shipment.find(1);

        shipment.addProduct(product, 1);
        ResultSet rs = db.query("select quantity_ordered from shipment_product where shipment_shipment_id = 1 and product_product_id = 1");
        rs.next();
        assertEquals("1", rs.getString("quantity_ordered"));

        shipment.updateQuantity(product, 2);
        rs = db.query("select quantity_ordered from shipment_product where shipment_shipment_id = 1 and product_product_id = 1");
        rs.next();
        assertEquals("2", rs.getString("quantity_ordered"));


        shipment.removeProduct(product);
        rs = db.query("select quantity_ordered from shipment_product where shipment_shipment_id = 1 and product_product_id = 1");
        assertFalse(rs.next());

    }

    @Test
    void finalizeShippment() throws SQLException {
        shipment.find(3);
        shipment.finalizeShipment();

        db.query("update shipment set fulfilled = false where shipment_id = 3");

    }
  
}
