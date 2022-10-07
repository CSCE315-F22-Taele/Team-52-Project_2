package edu.tamu.spinnstone.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.tamu.spinnstone.models.sql.Database;
import edu.tamu.spinnstone.models.sql.Table;

public class Shipment extends Table {
  public long shipmentId;
  public Date shipmentDate;
  public Boolean fulfilled;
  
  public Shipment(Database db) {
    super(db);
    tableName = "shipment";
    columnNames = Arrays.asList("shipment_id", "shipment_date", "fulfilled");
    columnTypes = Arrays.asList(ColumnType.LONG, ColumnType.DATE, ColumnType.BOOLEAN);
  }

  // region overrides

  @Override
  public ArrayList<Object> getColumnValues() {
    return new ArrayList<Object>(Arrays.asList(
      this.shipmentId,
      this.shipmentDate,
      this.fulfilled
    ));
  }

  @Override
  public void setColumnValues(List<Object> values) {
    this.shipmentId = (long) values.get(0);
    this.shipmentDate = (Date) values.get(1);
    this.fulfilled = (Boolean) values.get(2);
  }

  // endregion

  
  // region static methods

  // endregion


  
}
