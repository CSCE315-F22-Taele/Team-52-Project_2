package edu.tamu.spinnstone.models.sql;

import java.sql.*;
import java.util.List;
import java.util.Properties;

public class Database {
    private String url;
    private String user;
    private String password;
    public Connection connection;

    public enum TableNames {
        ORDER_ITEM_PRODUCT("order_item_product"), SHIPMENT("shipment"), ORDER_ITEM("order_item"), SHIPMENT_PRODUCT("shipment_product"), ORDER("order"), PRODUCT("product"), MENU_ITEM("menu_item");

        public String name;

        TableNames(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    public Database() {
        this.url = "jdbc:postgresql://csce-315-db.engr.tamu.edu:5432/csce331_904_52";
        this.user = "csce331_904_kevin";
        this.password = "friendlyalpaca";
    }

    /**
     * @param url
     * @param user
     * @param password
     */
    public Database(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * @throws SQLException
     */
    public void connect() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        connection = DriverManager.getConnection(url, props);
    }

    /**
     * @throws SQLException
     */
    public void disconnect() throws SQLException {
        connection.close();
    }

    /**
     * @param query
     * @return
     * @throws SQLException
     */
    public ResultSet query(String query) throws SQLException {
        Statement stm = connection.createStatement();
        boolean success = stm.execute(query);
        if (success == false) {
            return null;
        }

        ResultSet rs = stm.getResultSet();
        if (!rs.next()) {
            return null;
        }
        return rs;
    }

    /**
     * @param tableName
     * @return
     */
    public Query.Insert insert(String tableName) {
        return new Query.Insert(tableName, this);
    }

    /**
     * @param selectClause
     * @return
     */
    public Query.Select select(String selectClause) {
        return new Query.Select(selectClause, this);
    }

    /**
     * @param columns
     * @return
     */
    public Query.Select select(List<String> columns) {
        return new Query.Select(columns, this);
    }

    /**
     * @param tableName
     * @return
     */
    public Query.Update update(String tableName) {
        return new Query.Update(tableName, this);
    }

    /**
     * @param tableName
     * @return
     */
    public Query.Delete delete(String tableName) {
        return new Query.Delete(tableName, this);
    }
}
