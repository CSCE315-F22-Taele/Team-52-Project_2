--  QUERY 1: table containing shipment date, quantity ordered, and if shipment was fulfilled 
SELECT shipment.shipment_date, shipment_product.quantity_ordered, shipment.shipment_id, shipment.fulfilled FROM shipment
    JOIN shipment_product ON shipment.shipment_id = shipment_product.shipment_shipment_id;

--  QUERY 2: get all items on an order by order id
SELECT order_date, menu_item_id, order_item_id FROM "order"
    JOIN order_item ON order_item.order_id = "order".order_id
    WHERE "order".order_id = ?;

--  QUERY 3: get view of all orders
SELECT * FROM "order";

--  QUERY 4: view all inventory (the products table)
SELECT * FROM product;

-- QUERY 5: view shipment history (the shipments table)
SELECT * FROM shipment;

-- QUERY 6: get all of the products associated with specific order item
SELECT product.* FROM order_item
    JOIN order_item_product ON order_item_product.order_item_order_item_id = order_item.order_item_id
    JOIN product ON order_item_product.product_product_id = product.product_id
    WHERE order_item.order_id = ?;

-- QUERY 7: find all products on a shipment 
SELECT product.* FROM shipment
    JOIN shipment_product ON shipment_product.shipment_shipment_id = shipment.shipment_id
    JOIN product ON shipment_product.product_product_id = product.product_id
    WHERE shipment.shipment_id = ?;

-- // QUERY 8: get all menu items on an order item by order item id
SELECT * FROM order_item
    JOIN menu_item ON menu_item.menu_item_id = order_item.order_item_id
    WHERE order_item.order_item_id = ?;

-- // QUERY 9: get list of all menu items
SELECT * FROM menu_item;

-- // QUERY 10: set shipment to fulfilled
UPDATE shipment SET fulfilled = true WHERE shipment.shipment_id = ?;

-- // QUERY 11: update product quantity
UPDATE product SET quantity_in_stock = ? WHERE product.product_id = ?;

-- // QUERY 12: update price by menu_item_id
UPDATE menu_item SET menu_item_price = ? WHERE menu_item.menu_item_id = ?;

-- // QUERY 13: delete shipment by shipment_id
DELETE FROM shipment WHERE shipment.shipment_id = ?;

-- // QUERY 14: delete menu_item by menu_item_id
DELETE FROM menu_item WHERE menu_item.menu_item_id = ?;

-- // QUERY 15: delete product by product_id
DELETE FROM product WHERE product.product_id = ?;


-- // Extra queries for future interactivity

-- // Add a new shipment, TODO needs to update linking table (add products & amounts in shipment)
insert into shipment (shipment_date, fulfilled) values ('09/18/2022', false)

-- // Calculate cost of order (to update order table)
select sum(menu_item_price) from order_item
	join menu_item on menu_item.menu_item_id = order_item.menu_item_id
    where order_item.order_id = ?;
    
-- // update order with new cost
update "order" set order_total = ? where order_id = 1

-- // add new product to shipment /  update existing shipping entry

insert into shipment_product (shipment_shipment_id, product_product_id, quantity_ordered) values ()
update shipment_product set quantity_ordered =  69 where shipment_shipment_id = 1 and product_product_id = 1