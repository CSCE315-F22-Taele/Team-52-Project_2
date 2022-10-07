package edu.tamu.spinnstone.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Product extends PgObject {
    public long productId;
    public String productName;
    public double quantityInStock;




    public Product(Connection conn, long productId, String productName, double quantityInStock) throws SQLException {
        super(
          conn,
          "product",
          Arrays.asList("product_id", "product_name", "quantity_in_stock"),
          Arrays.asList(ColumnType.LONG, ColumnType.STRING, ColumnType.DOUBLE)
        );

      this.productId = productId;
      this.productName = productName;
      this.quantityInStock = quantityInStock;

    }

    
    public long insert() throws SQLException {
      Object[] values = {
        this.productId,
        this.productName,
        this.quantityInStock
      };
      
      return super.insert(
        values
      );
    }

    public Boolean updateQuantity(double quantity) throws SQLException {
      // returns true if the update was successful, false otherwise
      return connection.createStatement()
        .executeUpdate(
          String.format(
            "UPDATE product SET quantity_in_stock = %s WHERE product_id = %s",
            quantity,
            productId
          )
        ) > 0;
    }

    public static ArrayList<Product> getAll(Connection conn) throws SQLException {
      PreparedStatement statement = conn.prepareStatement(
        "SELECT * FROM product"
      );

      ResultSet rs = statement.executeQuery();

      ArrayList<Product> products = new ArrayList<Product>();

      while (rs.next()) {
        Product p = new Product(
          conn,
          rs.getLong("product_id"),
          rs.getString("product_name"),
          rs.getDouble("quantity_in_stock")
        );

        products.add(p);
      }

      return products;
    }

    public static Product create(Connection conn, String name, Double quantityInStock) throws SQLException {
      Product p = new Product(
        conn,
        0,
        name,
        quantityInStock
      );

      long productId = p.insert();
      p.productId = productId;

      return p;
    }

}
