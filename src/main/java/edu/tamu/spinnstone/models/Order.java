package edu.tamu.spinnstone.models;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.tamu.spinnstone.models.sql.Database;
import edu.tamu.spinnstone.models.sql.Table;

public class Order extends Table {
    public long orderId;
    public Date orderDate;
    public BigDecimal orderTotal;
    


    public Order(Database db) {
      super(db);
      tableName = "\"order\"";
      columnNames = new ArrayList<String>(Arrays.asList("order_id", "order_date", "order_total"));
      columnTypes = new ArrayList<ColumnType>(Arrays.asList(ColumnType.LONG, ColumnType.DATE, ColumnType.MONEY));
    }

    // region overrides

    @Override
    public ArrayList<Object> getColumnValues() {
      return new ArrayList<Object>(Arrays.asList(
        this.orderId,
        this.orderDate,
        this.orderTotal
      ));
    }

    @Override
    public void setColumnValues(List<Object> values) {
      this.orderId = (long) values.get(0);
      this.orderDate = (Date) values.get(1);
      this.orderTotal = (BigDecimal) values.get(2);
    }

    // endregion


}
