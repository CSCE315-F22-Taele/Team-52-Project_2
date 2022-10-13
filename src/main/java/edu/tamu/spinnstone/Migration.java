package edu.tamu.spinnstone;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.List;

import edu.tamu.spinnstone.models.MenuItem;
import edu.tamu.spinnstone.models.Order;
import edu.tamu.spinnstone.models.OrderItem;
import edu.tamu.spinnstone.models.Product;
import edu.tamu.spinnstone.models.Shipment;
import edu.tamu.spinnstone.models.sql.Database;

// migration to be fixed once tests are done for basic functionality
public class Migration {
  
  Connection connection;
  String projectRoot;


  public Migration(String username, String password, String databaseUrl) throws SQLException {
    String url = databaseUrl;
    Properties props = new Properties();
    props.setProperty("user", username);
    props.setProperty("password", password);
    this.connection = DriverManager.getConnection(url, props);
    this.projectRoot = System.getProperty("user.dir");
  }

  public void createTables() throws SQLException, IOException {
    String sql = new String(Files.readAllBytes(Paths.get(projectRoot, "src/sql/build_tables.sql")));
    connection.createStatement().execute(sql);
  }

  public void dropTables() throws SQLException, IOException {
    String sql = new String(Files.readAllBytes(Paths.get(projectRoot, "src/sql/drop_tables.sql")));
    connection.createStatement().execute(sql);
  }


  public Order generateRandomOrder(Database db, MenuItem menu, Product products, boolean gameday, Date orderDate) throws SQLException {
    Order order = new Order(db);
    OrderItem orderItem = new OrderItem(db);
    Product product = new Product(db);

    for(int i = 0; i < 3; i++) {
      // Pick a random item on the Menu
      int randomIndex = (int) (Math.random() * 6);
      menu.find(randomIndex);

      // Add menu item to order
      order.addOrderItem(orderItem);

      for(int j = 0; j < 5; j++) {
        // Choose a random product
        int productIndex = (int) (Math.random() * 31);
        product.find(productIndex);

        // Add product to order_item
        orderItem.sync();
        orderItem.addProduct(product);
        orderItem.update();
      }
    }

    order.orderDate = orderDate;
    order.orderId = order.insert();

    return order;
  } 


  public void populate() throws SQLException, IOException  { 

    // Product Name, Inventory Count, oz. in serving (1 indicates unitless)
    // 8 oz Dough, 4 oz Sauce, 6 oz Cheese, 1 oz Topping, 0.5 oz Sauce
    String[][] products = {
        {"Fountain Cup"   , "5000", "1"  },
        {"Bottle Beverage", "5000", "1"  },
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
        {"Oregano"        , "500" , "0.1"},
        {"Ranch"          , "1000", "0.5"},
        {"Sriracha"       , "0.5" , "0.5"}, 
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

     String[][] menuItems = {
      {"1 Topping Pizza",        "7.79"},
      {"2-4 Topping Pizza",      "8.99"},
      {"Original Cheese Pizza",  "6.79"},
      {"Bottled Beverage",       "2.39"},
      {"Gatorade",               "2.39"},
      {"Fountain Drink",         "1.99"}
     };

    dropTables();
    createTables();

    Database db = new Database();
    db.connect();

    Product product =  new Product(db);
    MenuItem menu = new MenuItem(db);
    Shipment shipment = new Shipment(db);

    // Add products to inventory

    for (String[] p : products) {
      product.productName = p[0];
      product.quantityInStock = Integer.parseInt(p[1]);
      product.conversion_factor = Float.parseFloat(p[2]);
      product.productId = product.insert();
    }
    product.sync();

    //Adds menu options to Menu Items

    for (int i = 0; i < menuItems.length; i++) {
      menu.itemName = menuItems[i][0];
      menu.menuItemPrice = new BigDecimal(menuItems[i][1]);
      menu.menuItemId = menu.insert();
    }
    menu.sync();
    
    // generate and add random orders to database
    // 215 orders per normal day, 400 orders per gameday
    // goes from 9/4 to 9/24; game days are 9/10 and 9/24

    // TODO: Change order totals to reflect $15k/week requirement

    {
      Date gameday1 = Date.valueOf(LocalDate.of(2022, 9, 10));
      Date gameday2 = Date.valueOf(LocalDate.of(2022, 9, 24));

      for(int day = 4; day < 25; ++day){
        Date date = Date.valueOf(LocalDate.of(2022, 9, day));
        int numOrders = 10;
        boolean gameday = false;
        
        if(date.compareTo(gameday1) == 0 || date.compareTo(gameday2) == 0){
          numOrders = 20;
          gameday = true;
        }

        for(int order = 0; order < numOrders; ++order){
          generateRandomOrder(db, menu, product, gameday, date).placeOrder();
        }
      }
    }
  
    // create shipments
    for (int i = 0; i < 3; ++i) {
      // For each product
      for (int j = 0; j < 31; ++j) {
        // Add product to shipment
        int qty = (500 + (int) (200 * Math.random()));
        product.find(j);
        shipment.addProduct(product, qty);
      }
      
      // Create shipment
      shipment.shipmentDate = new Date(2020,1,(i + 1) * 8);
      shipment.fulfilled = false;
      shipment.shipmentId = shipment.insert();
    }
    shipment.sync();
  }
}
