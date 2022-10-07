package edu.tamu.spinnstone;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

import edu.tamu.spinnstone.models.MenuItem;
import edu.tamu.spinnstone.models.Order;
import edu.tamu.spinnstone.models.OrderItem;
import edu.tamu.spinnstone.models.Product;
import edu.tamu.spinnstone.models.Shipment;

// migration to be fixed once tests are done for basic functionality
public class Migration {
  
  // Connection connection;
  // String projectRoot;


  // public Migration(String username, String password, String databaseUrl) throws SQLException {
  //   String url = databaseUrl;
  //   Properties props = new Properties();
  //   props.setProperty("user", username);
  //   props.setProperty("password", password);
  //   this.connection = DriverManager.getConnection(url, props);
  //   this.projectRoot = System.getProperty("user.dir");
  // }

  // public void createTables() throws SQLException, IOException {
  //   String sql = new String(Files.readAllBytes(Path.of(projectRoot, "src/sql/build_tables.sql")));
  //   connection.createStatement().execute(sql);
  // }

  // public void dropTables() throws SQLException, IOException {
  //   String sql = new String(Files.readAllBytes(Path.of(projectRoot, "src/sql/drop_tables.sql")));
  //   connection.createStatement().execute(sql);
  // }


  // public ArrayList<Order> generateRandomOrder(ArrayList<MenuItem> menu, ArrayList<Product> products, boolean gameday, Date orderDate) throws SQLException {
    
  //   // choose a random menu item from the menu
  //   ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();

  //   ArrayList<Order> orders = new ArrayList<Order>();
    
  //   Order order = Order.create(connection, orderDate, new BigDecimal("0.0"));

  //   for(int i = 0; i < 3; i++) {
  //     int randomIndex = (int) (Math.random() * menu.size());
  //     MenuItem menuItem = menu.get(randomIndex);
  //     OrderItem orderItem = OrderItem.create(this.connection, order.orderId, menuItem.menuItemId);
  //     orderItems.add(orderItem);
  //     // choose a random product from the products
  //     for(int j = 0; j < 5; j++) {
  //       int productIndex = (int) (Math.random() * products.size());
  //       Product product = products.get(productIndex);
  //       orderItem.addProduct(product.productId);
  //     }
  //   }

  //   return orders;
    
    
  // } 


  // public void populate() throws SQLException, IOException  { 
  //   String[] productNames = {
  //       "Fountain Cup",
  //       "Bottle Beverage",
  //       "Gatorade",
  //       "Cauliflower",
  //       "Standard",
  //       "Alfredo",
  //       "Traditional Red",
  //       "Zesty Red",
  //       "House Blend",
  //       "Parmesan",
  //       "BBQ Sauce",
  //       "Olive Oil",
  //       "Oregano",
  //       "Ranch",
  //       "Sriracha",
  //       "Diced Ham",
  //       "Italian Sausage",
  //       "Meatball",
  //       "Pepperoni",
  //       "Salami",
  //       "Smoked Chicken",
  //       "Banana Peppers",
  //       "Black Olives",
  //       "Green Peppers",
  //       "Jalapenos",
  //       "Mushrooms",
  //       "Onions",
  //       "Pineapple",
  //       "Roasted Garlic",
  //       "Spinach",
  //       "Tomatoes"
  //   };

  //    String[] menuItems = {
  //     "one topping pizza",
  //     "two - four topping pizza",
  //     "orginal cheese pizza",
  //     "bottled beverage",
  //     "gatorade",
  //     "fountain drink",
  //    };
      

  //   String[] menuItemPrices = {
  //     "7.79",
  //     "8.99",
  //     "6.79",
  //     "2.39",
  //     "2.39",
  //     "1.99",
  //   };

  //   dropTables();
  //   createTables();

    
  //   // Product.getAll(connection).forEach(product -> {
  //   //   Double qty = Math.floor(Math.random() * 10000)/100;
  //   //   try {
  //   //     product.updateQuantity(qty);
  //   //   } catch (Exception e) {
  //   //     e.printStackTrace();
  //   //   }
  //   // });


    

  //   // // add products to inventory
  //   ArrayList<Product> products = new ArrayList<Product>();

  //   for (int i = 0; i < productNames.length; i++) {
  //     Double qty = Math.floor(Math.random() * 100)/100d;
  //     Product p = Product.create(this.connection, productNames[i], qty);
  //     products.add(p);
  //   }
    
  //   //Adds products to menu_item
  //   ArrayList<MenuItem> menu = new ArrayList<MenuItem>();
    
  //   for (int i = 0; i < menuItems.length; i++) {
  //     MenuItem m = MenuItem.create(this.connection, menuItems[i], new BigDecimal(menuItemPrices[i]));
  //     menu.add(m);
  //   }

    
  //   // generate and add random orders to database
  //   // 215 orders per normal day, 400 orders per gameday
  //   // goes from 9/4 to 9/24; game days are 9/10 and 9/24

  //   Date gameday1 = Date.valueOf(LocalDate.of(2022, 9, 10));
  //   Date gameday2 = Date.valueOf(LocalDate.of(2022, 9, 24));

  //   for(int day = 4; day < 25; ++day){
  //     Date date = Date.valueOf(LocalDate.of(2022, 9, day));
  //     int numOrders = 10;
  //     boolean gameday = false;
      
  //     if(date.compareTo(gameday1) == 0 || date.compareTo(gameday2) == 0){
  //       numOrders = 20;
  //       gameday = true;
  //     }

  //     for(int order = 0; order < numOrders; ++order){
  //       generateRandomOrder(menu, products, gameday, date);
  //     }
  //   }

  //   // create shipments
  //   for (int i = 0; i <= 2; ++i) {
  //     Shipment shipment = Shipment.create(connection, new Date(2020,1,(i + 1) * 8), false);

  //     // For each product
  //     for (Product product : products) {
  //       // Add product to shipment
  //       int qty = (500 + (int) (200 * Math.random()));
  //       shipment.addProduct(product.productId, qty);
  //     }
  //   }
  // }
}
