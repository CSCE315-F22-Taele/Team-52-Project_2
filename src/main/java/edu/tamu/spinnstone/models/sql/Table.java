package edu.tamu.spinnstone.models.sql;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Table {

    public static enum ColumnType {
        STRING,
        FLOAT,
        DATE,
        INT,
        BOOL,
        LONG,
        DOUBLE,
        BOOLEAN,
        MONEY
    }

    public String tableName;
    public List<String> columnNames;
    public List<ColumnType> columnTypes;
    public Database database;

    public Table(Database database) {
        this.database = database;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    // region abstract methods
    // subclass must override these methods for crud operations to work
    public ArrayList<Object> getColumnValues() {
        throw new UnsupportedOperationException("getColumnValues not implemented");
    }

    public void setColumnValues(List<Object> values) {
        throw new UnsupportedOperationException("setColumnValues not implemented");
    }
    // endregion

    // region helpers

    protected void setStatementValue(
            PreparedStatement statement,
            int index,
            Object value
    ) throws SQLException {
        if (value instanceof String) {
            statement.setString(index, (String) value);
        } else if (value instanceof Float) {
            statement.setFloat(index, (Float) value);
        } else if (value instanceof Date) {
            statement.setDate(index, (Date) value);
        } else if (value instanceof Integer) {
            statement.setInt(index, (Integer) value);
        } else if (value instanceof Boolean) {
            statement.setBoolean(index, (Boolean) value);
        } else if (value instanceof Long) {
            statement.setLong(index, (Long) value);
        } else if (value instanceof Double) {
            statement.setDouble(index, (Double) value);
        } else if (value instanceof BigDecimal) {
            statement.setBigDecimal(index, (BigDecimal) value);
        } else if (value == null) {
            statement.setNull(index, java.sql.Types.NULL);
        } else {
            throw new UnsupportedOperationException("Unsupported type");
        }
    }

    protected List<String> prepareValues(List<Object> values) throws SQLException {
        // prepare a list of values for use in a sql statement
        PreparedStatement statement = database.connection.prepareStatement(
                String.join("~", values.stream().map(v -> "?").collect(Collectors.toList()))
        );
        for (int i = 0; i < values.size(); i++) {
            Object value = values.get(i);
            if (value == null) {
                // support optional parameters
                continue;
            }
            setStatementValue(statement, i + 1, values.get(i));
        }
        return Arrays.asList(statement.toString().split("~"));
    }

    protected String prepareValue(Object value) throws SQLException {
        // prepare a single value for use in a sql statement
        return prepareValues(new ArrayList<Object>(Arrays.asList(value))).get(0);
    }

    private Object getResultSetValue(ResultSet resultSet, int index) throws SQLException {
        // get a result set value at the given index
        // the columntypes are 0 indexed, the result set is 1 indexed
        switch (columnTypes.get(index)) {
            case STRING:
                return resultSet.getString(index + 1);
            case DOUBLE:
                return resultSet.getDouble(index + 1);
            case FLOAT:
                return resultSet.getFloat(index + 1);
            case DATE:
                return resultSet.getDate(index + 1);
            case INT:
                return resultSet.getInt(index + 1);
            case BOOL:
                return resultSet.getBoolean(index + 1);
            case LONG:
                return resultSet.getLong(index + 1);
            case BOOLEAN:
                return resultSet.getBoolean(index + 1);
            case MONEY:
                return resultSet.getBigDecimal(index + 1);
        }
        return null;
    }


    public void updateFromResultSet(ResultSet rs) throws SQLException {
        ArrayList<Object> values = new ArrayList<Object>();
        for (int i = 0; i < columnNames.size(); i++) {
            values.add(getResultSetValue(rs, i));
        }
        setColumnValues(values);
    }

    // endregion

    // region crud operations
    public long insert() throws SQLException {
        // right now the caller is expected to populate all fields on the object before calling insert
        Query query = database.insert(tableName)
                .columns(columnNames.subList(1, columnNames.size()))
                .values(
                        prepareValues(getColumnValues().subList(1, getColumnValues().size()))
                )
                .returning(columnNames.get(0));

        ResultSet rs = query.execute();

        rs.next();
        long id = rs.getLong(1);

        return id;
    }

    public void update() throws SQLException {

        ArrayList<Object> values = this.getColumnValues();

        Map<String, Object> setMap = new HashMap<String, Object>();

        for (int i = 1; i < columnNames.size(); i++) {
            setMap.put(columnNames.get(i), prepareValue(values.get(i)));
        }

        ResultSet result = database.update(tableName)
                .set(setMap)
                .where(columnNames.get(0), values.get(0))
                .returning(columnNames.get(0))
                .execute();

        if (result.next()) {
            // we are updating id in case this was a new object
            values.set(0, result.getLong(1));
            setColumnValues(values);
        }
    }


    public void delete() throws SQLException {
        database.delete(tableName)
                .where(columnNames.get(0), getColumnValues().get(0))
                .execute();
    }

    public boolean sync() throws SQLException {
        // return true if successful, false if not
        ResultSet rs = database.select(columnNames)
                .from(tableName)
                .where(columnNames.get(0), getColumnValues().get(0))
                .execute();

        boolean exists = rs.next();

        if (exists) {
            updateFromResultSet(rs);
        }

        return exists;
    }

    public boolean find(long id) throws SQLException {
        // true if found, false if not found
        // same as sync but intended to be used for a new object
        // a little awkward

        Query query = database.select(columnNames)
                .from(tableName)
                .where(columnNames.get(0), prepareValue(id));

        ResultSet rs = query.execute();

        if (rs.next()) {
            updateFromResultSet(rs);
            return true;
        }

        return false;
    }


    public ResultSet findWhere(String where, String orderBy, int limit) throws SQLException {
        Query query = database.select(columnNames).from(tableName);
        if (where != null) {
            query.where(where);
        }
        if (orderBy != null) {
            query.orderBy(orderBy);
        }
        if (limit > 0) {
            query.limit(limit);
        }

        ResultSet rs = query.execute();

        if(rs.next()) {
            return rs;
        }

        return null;
    }

    public ResultSet getView() throws SQLException {
        return findWhere(null, columnNames.get(0), -1);
    }

    // endregion

}
