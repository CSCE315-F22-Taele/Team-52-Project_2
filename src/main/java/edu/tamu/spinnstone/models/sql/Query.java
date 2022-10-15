package edu.tamu.spinnstone.models.sql;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class Query {
    /*
     * Below I am defining a set of classes that will be used to build a query.
     * In particular I am creating the idea of a clause which matches the SQL
     * concept of a clause (e.g. WHERE, ORDER BY, etc.). I am then using the
     * query options to define which clauses are relevant to which queries.
     */

    // region enums

    private enum QueryType {
        SELECT,
        INSERT,
        UPDATE,
        DELETE
    }

    private enum ClauseType {
        FROM,
        WHERE,
        ORDER_BY,
        LIMIT,
        OFFSET,
        VALUES,
        SET,
        RETURNING,
        TABLENAME,
        SELECT,
        ORDER,
        COLUMNS
    }

    // endregion

    protected QueryType queryType;
    protected HashMap<ClauseType, String> clauses;
    protected List<ClauseType> requiredClauses;
    protected List<ClauseType> optionalClauses;
    protected Database database;

    public Query(QueryType queryType, HashMap<ClauseType, String> clauses, List<ClauseType> requiredClauses, List<ClauseType> optionalClauses) {
        this.queryType = queryType;
        this.clauses = clauses;
        this.requiredClauses = requiredClauses;
        this.optionalClauses = optionalClauses;
    }

    private void setStatementValue(
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

    private List<String> prepareValues(List<Object> values) {
        // prepare a list of values for use in a sql statement
        try {
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
        } catch (Exception e) {
            return new ArrayList<String>();
        }

    }

    private String prepareValue(Object value) {
        // prepare a single value for use in a sql statement
        return prepareValues(new ArrayList<Object>(Arrays.asList(value))).get(0);
    }

    // make sure that all required clauses are present
    public ArrayList<ClauseType> missingRequiredClauses() {
        ArrayList<ClauseType> missingRequiredClauses = new ArrayList<ClauseType>();
        for (ClauseType clauseType : this.requiredClauses) {
            if (!this.clauses.containsKey(clauseType)) {
                missingRequiredClauses.add(clauseType);
            }
        }
        return missingRequiredClauses;
    }

    public ResultSet execute(Database database) throws SQLException {
        ArrayList<ClauseType> missingRequiredClauses = this.missingRequiredClauses();

        if (missingRequiredClauses.size() > 0) {
            throw new SQLException(String.format("Missing required clauses: %s", missingRequiredClauses.toString()));
        }

        Statement statement = database.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        boolean resultSet = statement.execute(this.toString());


        if (resultSet) {
            return statement.getResultSet();
        }

        return null;

    }

    public ResultSet execute() throws SQLException {
        if (this.database == null) {
            throw new SQLException("No database set");
        }
        return this.execute(this.database);
    }

    public void forEach(Consumer<ResultSet> consumer) throws SQLException {
        ResultSet resultSet = this.execute();
        while (resultSet.next()) {
            consumer.accept(resultSet);
        }
    }


    // region static methods

    public static Select select(String selectClause) {
        return new Select(selectClause);
    }

    public static Select select(String selectClause, Database database) {
        return new Select(selectClause, database);
    }

    public static Select select(List<String> columnNames) {
        return new Select(columnNames);
    }

    public static Select select(List<String> columnNames, Database database) {
        return new Select(columnNames, database);
    }

    public static Insert insert(String tableName) {
        return new Insert(tableName);
    }

    public static Insert insert(String tableName, Database database) {
        return new Insert(tableName, database);
    }

    public static Update update(String tableName) {
        return new Update(tableName);
    }

    public static Update update(String tableName, Database database) {
        return new Update(tableName, database);
    }

    public static Delete delete(String tableName) {
        return new Delete(tableName);
    }

    public static Delete delete(String tableName, Database database) {
        return new Delete(tableName, database);
    }

    // endregion

    // region helpers

    public String convertToSqlString(Object value) throws SQLException {
        PreparedStatement statement = database.connection.prepareStatement("?");
        if (value instanceof String) {
            statement.setString(1, (String) value);
        }

        return statement.toString();
    }
    // endregion

    // region clauses

    public Query where(String whereClause) {
        this.clauses.put(ClauseType.WHERE, whereClause);
        return this;
    }

    public Query where(String whereTemplate, Object... whereArgs) {
        this.clauses.put(ClauseType.WHERE, String.format(whereTemplate, whereArgs));
        return this;
    }

    public Query where(String key, Object value) {
        this.clauses.put(ClauseType.WHERE, String.format("%s = %s", key, prepareValue(value)));
        return this;
    }

    public Query where(Map<String, Object> whereMap) {
        ArrayList<String> whereClauses = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : whereMap.entrySet()) {
            whereClauses.add(String.format("%s = %s", entry.getKey(), prepareValue(entry.getValue())));
        }
        this.clauses.put(ClauseType.WHERE, String.join(", ", whereClauses));
        return this;
    }

    public Query limit(String limitClause) {
        this.clauses.put(ClauseType.LIMIT, limitClause);
        return this;
    }

    public Query limit(int limit) {
        this.clauses.put(ClauseType.LIMIT, Integer.toString(limit));
        return this;
    }

    public Query columns(String... columns) {
        this.clauses.put(ClauseType.COLUMNS, String.join(", ", columns));
        return this;
    }

    public Query columns(String columnsClause) {
        this.clauses.put(ClauseType.COLUMNS, columnsClause);
        return this;
    }

    public Query columns(List<String> columns) {
        this.clauses.put(ClauseType.COLUMNS, String.join(", ", columns));
        return this;
    }

    public Query values(Object... values) {
        this.clauses.put(ClauseType.VALUES, String.join(", ", prepareValues(Arrays.asList(values))));
        return this;
    }

    public Query values(List<String> values) {
        this.clauses.put(ClauseType.VALUES, String.join(", ", values));
        return this;
    }

    public Query returning(String returningClause) {
        this.clauses.put(ClauseType.RETURNING, returningClause);
        return this;
    }

    public Query orderBy(String orderByClause) {
        this.clauses.put(ClauseType.ORDER_BY, orderByClause);
        return this;
    }

    public Query offset(String offsetClause) {
        this.clauses.put(ClauseType.OFFSET, offsetClause);
        return this;
    }

    public Query from(String fromClause) {
        this.clauses.put(ClauseType.FROM, fromClause);
        return this;
    }

    public Query set(String setClause) {
        this.clauses.put(ClauseType.SET, setClause);
        return this;
    }


    public Query set(Map<String, Object> setMap) {
        ArrayList<String> setClauses = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : setMap.entrySet()) {
            setClauses.add(String.format("%s = %s", entry.getKey(), prepareValue(entry.getValue())));
        }
        this.clauses.put(ClauseType.SET, String.join(", ", setClauses));
        return this;
    }

    // endregion


    // region query types

    public static class Select extends Query {
        public Select(String selectClause) {
            super(
                    QueryType.SELECT,
                    new HashMap<ClauseType, String>() {{
                        put(ClauseType.SELECT, selectClause);
                    }},
                    new ArrayList<ClauseType>(Arrays.asList(
                            ClauseType.FROM
                    )),
                    new ArrayList<ClauseType>(Arrays.asList(
                            ClauseType.WHERE,
                            ClauseType.ORDER_BY,
                            ClauseType.LIMIT,
                            ClauseType.OFFSET
                    ))
            );
        }

        public Select(String selectClause, Database database) {
            this(selectClause);
            this.database = database;
        }

        public Select(List<String> columns, Database database) {
            this(String.join(",", columns));
            this.database = database;
        }

        public Select(List<String> columns) {
            this(String.join(",", columns));
        }

        public String toString() {
            return String.format(
                    "SELECT %s FROM %s%s%s%s%s",
                    this.clauses.get(ClauseType.SELECT),
                    this.clauses.get(ClauseType.FROM),
                    this.clauses.containsKey(ClauseType.WHERE) ? " WHERE " + this.clauses.get(ClauseType.WHERE) : "",
                    this.clauses.containsKey(ClauseType.ORDER_BY) ? " ORDER BY " + this.clauses.get(ClauseType.ORDER_BY) : "",
                    this.clauses.containsKey(ClauseType.LIMIT) ? " LIMIT " + this.clauses.get(ClauseType.LIMIT) : "",
                    this.clauses.containsKey(ClauseType.OFFSET) ? " OFFSET " + this.clauses.get(ClauseType.OFFSET) : ""
            );
        }

    }

    public static class Insert extends Query {
        public Insert(String tableName) {
            super(
                    QueryType.INSERT,
                    new HashMap<ClauseType, String>() {{
                        put(ClauseType.TABLENAME, tableName);
                    }},
                    new ArrayList<ClauseType>(Arrays.asList(
                            ClauseType.TABLENAME,
                            ClauseType.COLUMNS,
                            ClauseType.VALUES
                    )),
                    new ArrayList<ClauseType>(Arrays.asList(
                            ClauseType.RETURNING
                    ))
            );
        }

        public Insert(String tableName, Database database) {
            this(tableName);
            this.database = database;
        }

        public String toString() {
            return String.format(
                    "INSERT INTO %s (%s) VALUES (%s)%s",
                    this.clauses.get(ClauseType.TABLENAME),
                    this.clauses.get(ClauseType.COLUMNS),
                    this.clauses.get(ClauseType.VALUES),
                    this.clauses.containsKey(ClauseType.RETURNING) ? " RETURNING " + this.clauses.get(ClauseType.RETURNING) : ""
            );
        }
    }

    public static class Update extends Query {
        public Update(String tableName) {
            super(
                    QueryType.UPDATE,
                    new HashMap<ClauseType, String>() {{
                        put(ClauseType.TABLENAME, tableName);
                    }},
                    new ArrayList<ClauseType>(Arrays.asList(
                            ClauseType.TABLENAME,
                            ClauseType.SET
                    )),
                    new ArrayList<ClauseType>(Arrays.asList(
                            ClauseType.WHERE,
                            ClauseType.RETURNING
                    ))
            );
        }

        public Update(String tableName, Database database) {
            this(tableName);
            this.database = database;
        }

        public Update set(String setClause) {
            this.clauses.put(ClauseType.SET, setClause);
            return this;
        }

        public Update where(String whereClause) {
            this.clauses.put(ClauseType.WHERE, whereClause);
            return this;
        }

        public Update returning(String returningClause) {
            this.clauses.put(ClauseType.RETURNING, returningClause);
            return this;
        }

        public String toString() {
            return String.format(
                    "UPDATE %s SET %s%s%s",
                    this.clauses.get(ClauseType.TABLENAME),
                    this.clauses.get(ClauseType.SET),
                    this.clauses.containsKey(ClauseType.WHERE) ? " WHERE " + this.clauses.get(ClauseType.WHERE) : "",
                    this.clauses.containsKey(ClauseType.RETURNING) ? " RETURNING " + this.clauses.get(ClauseType.RETURNING) : ""
            );
        }
    }

    public static class Delete extends Query {
        public Delete(String tableName) {
            super(
                    QueryType.DELETE,
                    new HashMap<ClauseType, String>() {{
                        put(ClauseType.TABLENAME, tableName);
                    }},
                    new ArrayList<ClauseType>(Arrays.asList(
                            ClauseType.TABLENAME
                    )),
                    new ArrayList<ClauseType>(Arrays.asList(
                            ClauseType.WHERE,
                            ClauseType.RETURNING
                    ))
            );
        }

        public Delete(String tableName, Database database) {
            this(tableName);
            this.database = database;
        }

        public String toString() {
            return String.format(
                    "DELETE FROM %s%s%s",
                    this.clauses.get(ClauseType.TABLENAME),
                    this.clauses.containsKey(ClauseType.WHERE) ? " WHERE " + this.clauses.get(ClauseType.WHERE) : "",
                    this.clauses.containsKey(ClauseType.RETURNING) ? " RETURNING " + this.clauses.get(ClauseType.RETURNING) : ""
            );
        }
    }

    // endregion

}
