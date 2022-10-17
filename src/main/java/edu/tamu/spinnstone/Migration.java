package edu.tamu.spinnstone;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import edu.tamu.spinnstone.models.MenuItem;
import edu.tamu.spinnstone.models.Order;
import edu.tamu.spinnstone.models.OrderItem;
import edu.tamu.spinnstone.models.Product;
import edu.tamu.spinnstone.models.Shipment;
import edu.tamu.spinnstone.models.sql.Database;
import edu.tamu.spinnstone.models.sql.Table;

// migration to be fixed once tests are done for basic functionality
public class Migration {

  String projectRoot;
  Database db;
  ArrayList<MenuItem> menuItems;
  ArrayList<Product> products;

  public Migration() throws SQLException {
    db = new Database();
    db.connect();
    this.projectRoot = System.getProperty("user.dir");
  }

  public void createTables() throws SQLException, IOException {
    String sql = new String(Files.readAllBytes(Paths.get(projectRoot, "src/sql/build_tables.sql")));
    db.connection.createStatement().execute(sql);
  }

  public void dropTables() throws SQLException, IOException {
    String sql = new String(Files.readAllBytes(Paths.get(projectRoot, "src/sql/drop_tables.sql")));
    db.connection.createStatement().execute(sql);
  }

  public Order generateRandomOrder(Date orderDate)
      throws SQLException {
    Order order = new Order(db);

    // add a pizza or two

    int numPizzas = (int) (Math.random() * 2) + 1;
    for (int i = 0; i < numPizzas; i++) {
      // select which pizza
      MenuItem pizza = menuItems.get((int) (Math.random() * 3));
      OrderItem orderItem = new OrderItem(db);
      orderItem.menuItem = pizza;

      for (int j = 0; j < 3; j++) {
        // add some toppings
        Product topping = products.get((int) (Math.random() * (products.size() - 3)) + 3);

        // Add menu item to order
        orderItem.products.add(topping);

      }

      order.orderItems.add(orderItem);
    }

    // add a drink
    MenuItem drink = menuItems.get((int) (Math.random() * 3) + 3);
    OrderItem orderItem = new OrderItem(db);
    orderItem.quantity = (int) (Math.random() * 3) + 1;
    switch(drink.itemName) {
      case "Fountain Drink":
        orderItem.menuItem = menuItems.get(5);
        orderItem.products.add(products.get(0));
        break;
      case "Bottled Beverage":
        orderItem.menuItem = menuItems.get(3);
        orderItem.products.add(products.get(1));
        break;
      case "Gatorade":
        orderItem.menuItem = menuItems.get(4);
        orderItem.products.add(products.get(2));
        break;
    }

    order.orderItems.add(orderItem);


    order.orderDate = orderDate;
    order.orderTotal = new BigDecimal(0);

    return order;
  }

  public static void main(String[] args) throws SQLException, IOException {
    Migration m = new Migration();
    m.populate();
  }

  public void populate() throws SQLException, IOException {
    // drop and recreate tables
    dropTables();
    createTables();

    // first create the menu-item categories and product types
    Product.ProductType[] productTypes =
        new Product.ProductType[] {
          Product.ProductType.DRINK,
          Product.ProductType.DRIZZLE,
          Product.ProductType.SAUCE,
          Product.ProductType.CHEESE,
          Product.ProductType.MEAT,
          Product.ProductType.VEGGIES,
          Product.ProductType.CRUST,
          Product.ProductType.OTHER,
        };

    for (Product.ProductType type : productTypes) {
      db.insert(Table.Names.PRODUCT_TYPE.toString())
          .columns("product_type_name")
          .values(type.toString())
          .execute();
    }

    // make a map for use in creating products assuming they were inserted
    // starting at 0
    Map<String, Integer> productTypeMap =
        new HashMap<String, Integer>() {
          {
            put(Product.ProductType.DRINK.toString(), 1);
            put(Product.ProductType.DRIZZLE.toString(), 2);
            put(Product.ProductType.SAUCE.toString(), 3);
            put(Product.ProductType.CHEESE.toString(), 4);
            put(Product.ProductType.MEAT.toString(), 5);
            put(Product.ProductType.VEGGIES.toString(), 6);
            put(Product.ProductType.CRUST.toString(), 7);
            put(Product.ProductType.OTHER.toString(), 8);
          }
        };

    MenuItem.Categories[] categories =
        new MenuItem.Categories[] {
          MenuItem.Categories.PIZZA, MenuItem.Categories.BEVERAGE, MenuItem.Categories.OTHER,
        };

    for (MenuItem.Categories category : categories) {
      db.insert(Table.Names.MENU_ITEM_CATEGORY.toString())
          .columns("menu_item_category_name")
          .values(category.toString())
          .execute();
    }

    // make a map for use in creating menu items assuming they were inserted
    // starting at 0
    Map<String, Integer> categoryMap =
        new HashMap<String, Integer>() {
          {
            put(MenuItem.Categories.PIZZA.toString(), 1);
            put(MenuItem.Categories.BEVERAGE.toString(), 2);
            put(MenuItem.Categories.OTHER.toString(), 3);
          }
        };

    // now create the products
    // Product Name, Inventory Count, oz. in serving (1 indicates unitless), Product Type
    // 8 oz Dough, 4 oz Sauce, 6 oz Cheese, 1 oz Topping, 0.5 oz Sauce
    String[][] productDefinitions = {
      {"Fountain Drink", "5000", "1", Product.ProductType.DRINK.toString()},
      {"Bottled Beverage", "5000", "1", Product.ProductType.DRINK.toString()},
      {"Gatorade", "1000", "1", Product.ProductType.DRINK.toString()},
      {"Cauliflower", "200", "8", Product.ProductType.CRUST.toString()},
      {"Standard", "3500", "8", Product.ProductType.CRUST.toString()},
      {"Alfredo", "1500", "4", Product.ProductType.SAUCE.toString()},
      {"Traditional Red", "3000", "4", Product.ProductType.SAUCE.toString()},
      {"Zesty Red", "2500", "4", Product.ProductType.SAUCE.toString()},
      {"House Blend", "2000", "6", Product.ProductType.CHEESE.toString()},
      {"Parmesan", "2000", "6", Product.ProductType.CHEESE.toString()},
      {"BBQ Sauce", "1000", "0.5", Product.ProductType.DRIZZLE.toString()},
      {"Olive Oil", "1000", "0.5", Product.ProductType.DRIZZLE.toString()},
      {"Oregano", "500", "0.5", Product.ProductType.DRIZZLE.toString()},
      {"Ranch", "1000", "0.5", Product.ProductType.DRIZZLE.toString()},
      {"Sriracha", "1000", "0.5", Product.ProductType.DRIZZLE.toString()},
      {"Diced Ham", "2000", "1", Product.ProductType.MEAT.toString()},
      {"Italian Sausage", "2000", "1", Product.ProductType.MEAT.toString()},
      {"Meatball", "2000", "1", Product.ProductType.MEAT.toString()},
      {"Pepperoni", "2000", "1", Product.ProductType.MEAT.toString()},
      {"Salami", "2000", "1", Product.ProductType.MEAT.toString()},
      {"Smoked Chicken", "2000", "1", Product.ProductType.MEAT.toString()},
      {"Banana Peppers", "2000", "1", Product.ProductType.VEGGIES.toString()},
      {"Black Olives", "2000", "1", Product.ProductType.VEGGIES.toString()},
      {"Green Peppers", "2000", "1", Product.ProductType.VEGGIES.toString()},
      {"Jalapenos", "2000", "1", Product.ProductType.VEGGIES.toString()},
      {"Mushrooms", "2000", "1", Product.ProductType.VEGGIES.toString()},
      {"Onions", "2000", "1", Product.ProductType.VEGGIES.toString()},
      {"Pineapple", "2000", "1", Product.ProductType.VEGGIES.toString()},
      {"Roasted Garlic", "2000", "1", Product.ProductType.VEGGIES.toString()},
      {"Spinach", "2000", "1", Product.ProductType.VEGGIES.toString()},
      {"Tomatoes", "2000", "1", Product.ProductType.VEGGIES.toString()}
    };

    products = new ArrayList<Product>();

    for (String[] p : productDefinitions) {
      products.add(
        Product.create(
            db, p[0], Integer.parseInt(p[1]), Float.parseFloat(p[2]), productTypeMap.get(p[3]))
      );
    }

    // now create the menu items

    String[][] menuItemsDefinitions = {
      {"1 Topping Pizza", "7.79", MenuItem.Categories.PIZZA.toString()},
      {"2-4 Topping Pizza", "8.99", MenuItem.Categories.PIZZA.toString()},
      {"Original Cheese Pizza", "6.79", MenuItem.Categories.PIZZA.toString()},
      {"Bottled Beverage", "2.39", MenuItem.Categories.BEVERAGE.toString()},
      {"Gatorade", "2.39", MenuItem.Categories.BEVERAGE.toString()},
      {"Fountain Drink", "1.99", MenuItem.Categories.BEVERAGE.toString()}
    };

    menuItems = new ArrayList<MenuItem>();

    // Adds menu options to Menu Items
    for (String[] menuItem : menuItemsDefinitions) {
      menuItems.add(
        MenuItem.create(
            db,
            menuItem[0],
            new BigDecimal(menuItem[1]),
            categoryMap.get(menuItem[2]),
            menuItem[2].equals(MenuItem.Categories.PIZZA.toString()) ? true : false)
      );
    }

    String[] pizzaOptions = {
      "Cauliflower",
      "Standard",
      "Alfredo",
      "Traditional Red",
      "Zesty Red",
      "House Blend",
      "Parmesan",
      "BBQ Sauce",
      "Olive Oil",
      "Oregano",
      "Ranch",
      "Sriracha",
      "Diced Ham",
      "Italian Sausage",
      "Meatball",
      "Pepperoni",
      "Salami",
      "Smoked Chicken",
      "Banana Peppers",
      "Black Olives",
      "Green Peppers",
      "Jalapenos",
      "Mushrooms",
      "Onions",
      "Pineapple",
      "Roasted Garlic",
      "Spinach",
      "Tomatoes"
    };

    // Adds options (products) to Menu Items
    ResultSet rs =
        db.query(
            "select menu_item_id from menu_item\n"
                + "join menu_item_category mic on menu_item.menu_item_category_id = mic.menu_item_category_id\n"
                + "where mic.menu_item_category_name = 'Pizza'");

    if (!rs.next()) {
      throw new RuntimeException("No menu items found while adding options to menu items");
    }

    while (rs.next()) {
      long mid = rs.getLong("menu_item_id");
      for (String option : pizzaOptions) {
        ResultSet prs = db.select("product_id")
                .from("product")
                .where("product_name", option)
                .execute();

        if (!prs.next()) {
          throw new RuntimeException("No product found while adding options to menu items");
        }

        long pid = prs.getLong("product_id");

        db.insert(Table.Names.MENU_ITEM_PRODUCT.toString())
            .columns("menu_item_menu_item_id", "product_product_id", "optional")
            .values(mid, pid, true)
            .execute();
      }
    }

    // now create the orders





    // generate and add random orders to database
    // 215 orders per normal day, 400 orders per gameday
    // goes from 9/4 to 9/24; game days are 9/10 and 9/24

    // TODO: Change order totals to reflect $15k/week requirement

    Date gameday1 = Date.valueOf(LocalDate.of(2022, 9, 10));
    Date gameday2 = Date.valueOf(LocalDate.of(2022, 9, 24));

    // multiplicity, increase for more orders

    for(int day = 4; day < 6; ++day){
      Date date = Date.valueOf(LocalDate.of(2022, 9, day));
      int numOrders = 2;
      boolean gameday = false;

      if(date.compareTo(gameday1) == 0 || date.compareTo(gameday2) == 0){
        numOrders = 10;
        gameday = true;
      }

      for(int order = 0; order < numOrders; ++order){
        generateRandomOrder(date).placeOrder();
      }
    }
    // create shipments
    Product product = new Product(db);

    for (int i = 0; i < 3; ++i) {

      // create the shipment first because Shipment::addProduct expects shipment to exist
      Shipment shipment = new Shipment(db);
      shipment.shipmentDate = new Date(2020,1,(i + 1) * 8);
      shipment.fulfilled = false;
      shipment.shipmentId = shipment.insert();

      // For each product
      for (int j = 1; j < 31; ++j) {
        // Add product to shipment
        int qty = (500 + (int) (200 * Math.random()));
        product.find(j);
        shipment.addProduct(product, qty);
      }
    }

    System.out.println("Database initialized, have fun!");
  }
}
