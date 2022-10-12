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


public class ProductTest {

    Database db;
    ProductTest() throws SQLException{
        db = new Database();
        db.connect();
    }


    @Test
    void getInventory() throws SQLException{
        Product product = new edu.tamu.spinnstone.models.Product(db);
        product.sync();
        ResultSet rs = product.getInventory();
        do
        {
        System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3));
        } while ((rs.next()));
    }
  
}
