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
    void getView() throws SQLException{
        Product product = new edu.tamu.spinnstone.models.Product(db);
        product.sync();
        ResultSet rs = product.getView();
        do
        {
        System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3));
        } while ((rs.next()));
    }

    @Test
    void updateQuantity() throws SQLException {
        Product product = new Product(db);
        product.find(1);
        product.updateQuantity(0);

        product.sync();
        assertEquals(0, product.quantityInStock);

        product.updateQuantity(4999);
    }

    @Test
    void populate() throws SQLException {

        String[][] products = {
            {"Fountain Drink"   , "5000", "1"  },
            {"Bottled Beverage", "5000", "1"  },
            {"Gatorade"       , "1000", "1"  },
            {"Cauliflower"    , "200" , "8"  },
            {"Standard"       , "3500", "8"  },
            {"Alfredo"        , "1500", "4"  },
            {"Traditional Red", "3000", "4"  },
            {"Zesty Red"      , "2500", "4"  },
            {"House Blend"    , "2000", "6"  },
            {"Parmesan"       , "2000", "6"  },
            {"BBQ Sauce"      , "1000", "0.5"},
            {"Olive Oil"      , "1000", "0.5"},
            {"Oregano"        , "500" , "0.5"},
            {"Ranch"          , "1000", "0.5"},
            {"Sriracha"       , "1000", "0.5"}, 
            {"Diced Ham"      , "2000", "1"  },
            {"Italian Sausage", "2000", "1"  }, 
            {"Meatball"       , "2000", "1"  },
            {"Pepperoni"      , "2000", "1"  },
            {"Salami"         , "2000", "1"  },
            {"Smoked Chicken" , "2000", "1"  },  
            {"Banana Peppers" , "2000", "1"  },    
            {"Black Olives"   , "2000", "1"  },
            {"Green Peppers"  , "2000", "1"  },
            {"Jalapenos"      , "2000", "1"  },
            {"Mushrooms"      , "2000", "1"  },
            {"Onions"         , "2000", "1"  },
            {"Pineapple"      , "2000", "1"  },
            {"Roasted Garlic" , "2000", "1"  },   
            {"Spinach"        , "2000", "1"  },
            {"Tomatoes"       , "2000", "1"  }
        };
        
        for (int i = 0; i < products.length; ++i) {
            db.query("update product set product_name = \'" + products[i][0] + "\' where product_id = " + i);
            db.query("update product set quantity_in_stock = " + Integer.parseInt(products[i][1]) + " where product_id = " + i);
            db.query("update product set conversion_factor = " + Float.parseFloat(products[i][2]) + " where product_id = " + i); 
        }

    }
  
}
