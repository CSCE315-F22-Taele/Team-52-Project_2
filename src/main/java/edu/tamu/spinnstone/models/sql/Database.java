package edu.tamu.spinnstone.models.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

public class Database {
    private String url;
    private String user;
    private String password;
    public Connection connection;

    public Database() {
        this.url = "jdbc:postgresql://csce-315-db.engr.tamu.edu:5432/csce331_904_52";
        this.user = "csce331_904_kevin";
        this.password = "friendlyalpaca";
    }

    public Database(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void connect() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        connection = DriverManager.getConnection(url, props);
    }

    public void disconnect() throws SQLException {
        connection.close();
    }

    public ResultSet query(String query) throws SQLException {
        Statement stm = connection.createStatement();
        boolean success = stm.execute(query);
        return success ? stm.getResultSet() : null;
    }

    public Query.Insert insert(String tableName) {
        return new Query.Insert(tableName, this);
    }

    public Query.Select select(String selectClause) {
        return new Query.Select(selectClause, this);
    }

    public Query.Select select(List<String> columns) {
        return new Query.Select(columns, this);
    }

    public Query.Update update(String tableName) {
        return new Query.Update(tableName, this);
    }

    public Query.Delete delete(String tableName) {
        return new Query.Delete(tableName, this);
    }
}
