package edu.tamu.spinnstone.models;

import edu.tamu.spinnstone.models.sql.Database;
import edu.tamu.spinnstone.models.sql.Table;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Shipment extends Table {
    public long shipmentId;
    public Date shipmentDate;
    public Boolean fulfilled;

    // product -> quantity ordered

    public Shipment(Database db) {
        super(db);
        tableName = "shipment";
        columnNames = Arrays.asList("shipment_id", "shipment_date", "fulfilled");
        columnTypes = Arrays.asList(ColumnType.LONG, ColumnType.DATE, ColumnType.BOOLEAN);
    }

    // region overrides

    @Override
    public ArrayList<Object> getColumnValues() {
        return new ArrayList<>(Arrays.asList(this.shipmentId, this.shipmentDate, this.fulfilled));
    }

    @Override
    public void setColumnValues(List<Object> values) {
        this.shipmentId = (long) values.get(0);
        this.shipmentDate = (Date) values.get(1);
        this.fulfilled = (Boolean) values.get(2);
    }

    // endregion

    // region static methods
    /**
     * Create new shipment in table
     * @param db
     * @param shipmentDate
     * @param fulfilled
     * @return
     * @throws SQLException
     */
    public static Shipment create(Database db, Date shipmentDate, Boolean fulfilled) throws SQLException {
        Shipment shipment = new Shipment(db);
        shipment.shipmentDate = shipmentDate;
        shipment.fulfilled = fulfilled;
        shipment.shipmentId = shipment.insert();

        return shipment;
    }

    // endregion

    /**
     * Add product to current shipment and update database
     * @param product
     * @param quantity
     * @throws SQLException
     */
    public void addProduct(Product product, double quantity) throws SQLException {
        database.insert("shipment_product").columns("shipment_shipment_id", "product_product_id", "quantity_ordered").values(shipmentId, product.productId, quantity).execute();
    }

    /**
     * Remove product from current shipment and update database
     * @param product
     * @throws SQLException
     */
    public void removeProduct(Product product) throws SQLException {
        database.query("delete from shipment_product where shipment_shipment_id = " + shipmentId + " and product_product_id = " + product.productId);
    }

    /**
     * Edit product quantity in current shipment and update databse
     * @param product
     * @param quantity
     * @throws SQLException
     */
    public void updateQuantity(Product product, int quantity) throws SQLException {
        database.query("update shipment_product set quantity_ordered = " + quantity + " where shipment_shipment_id = " + shipmentId + " and product_product_id = " + product.productId);
    }

    /**
     * @return true if current shipment is set as fulfilled, false otherwise
     */
    public boolean finalizeShipment() {
        fulfilled = true;
        try {
            update();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
