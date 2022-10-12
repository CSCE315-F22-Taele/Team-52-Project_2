package edu.tamu.spinnstone.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.tamu.spinnstone.models.sql.Database;
import edu.tamu.spinnstone.models.sql.Table;

public class Shipment extends Table {
    public long shipmentId;
    public Date shipmentDate;
    public Boolean fulfilled;

    // product -> quantity ordered
    public HashMap<Product, Integer> products;

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

    public static Shipment create(Database db, Date shipmentDate, Boolean fulfilled) throws SQLException {
        Shipment shipment = new Shipment(db);
        shipment.shipmentDate = shipmentDate;
        shipment.fulfilled = fulfilled;
        shipment.shipmentId = shipment.insert();

        return shipment;
    }

    // endregion

    public void addProduct(Product product, double quantity) throws SQLException {
        // returns true if the product was added to the shipment, false otherwise
        database.insert("shipment_product")
                .columns("shipment_shipment_id", "product_product_id", "quantity_ordered")
                .values(shipmentId, product.productId, quantity)
                .execute();
        // throw new UnsupportedOperationException("addProductToShipment not implemented");
    }

    public boolean removeProduct(Product product) {
        // returns true if the product was removed from the shipment, false otherwise
        throw new UnsupportedOperationException("removeProductFromShipment not implemented");
    }

    public boolean updateQuantity(Product product, int quantity) {
        // update the quantity of a product in the shipment
        // this should not update the table! assume anything that was persisted is already en-route
        // only update the quantity in the shipment object before finalizing
        throw new UnsupportedOperationException("updateQuantity not implemented");
    }

    public boolean finalizeShipment() {
        // returns true if the shipment was finalized, false otherwise
        throw new UnsupportedOperationException("finalizeShipment not implemented");
    }


}
