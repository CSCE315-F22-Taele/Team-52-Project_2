package edu.tamu.spinnstone.models;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.tamu.spinnstone.models.sql.Database;
import edu.tamu.spinnstone.models.sql.Table;

public class MenuItem extends Table {
    public long menuItemId;
    public String itemName;
    public BigDecimal menuItemPrice;


    public MenuItem(Database db) {
      super(db);
      tableName = "menu_item";
      columnNames = new ArrayList<String>(Arrays.asList("menu_item_id", "item_name", "menu_item_price"));
      columnTypes = new ArrayList<ColumnType>(Arrays.asList(ColumnType.LONG, ColumnType.STRING, ColumnType.MONEY));
    }

    // region overrides

    @Override
    public ArrayList<Object> getColumnValues() {
      return new ArrayList<Object>(Arrays.asList(
        this.menuItemId,
        this.itemName,
        this.menuItemPrice
      ));
    }

    @Override
    public void setColumnValues(List<Object> values) {
      this.menuItemId = (long) values.get(0);
      this.itemName = (String) values.get(1);
      this.menuItemPrice = (BigDecimal) values.get(2);
    }

    // endregion
    public boolean setPrice(BigDecimal price) throws SQLException {
      // sets the price of the menu item and returns true if successful
      throw new UnsupportedOperationException("Unimplemented");
    }


}
