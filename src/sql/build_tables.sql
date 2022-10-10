create table "order"
(
    order_id    bigint generated always as identity
        primary key,
    order_date  date  not null,
    order_total money not null
);


grant ALL PRIVILEGES on "order" to public;

create table product
(
    product_id        bigint generated always as identity
        primary key,
    product_name      text                       not null,
    quantity_in_stock double precision default 0 not null
);


grant ALL PRIVILEGES on product to public;

create table shipment
(
    shipment_id   bigint generated always as identity
        primary key,
    shipment_date date    not null,
    fulfilled     boolean not null
);


grant ALL PRIVILEGES on shipment to public;

create table shipment_product
(
    shipment_shipment_id bigint                     not null
        references shipment,
    product_product_id   bigint                     not null
        references product,
    quantity_ordered     double precision default 0 not null
);


grant ALL PRIVILEGES on shipment_product to public;

create table menu_item
(
    menu_item_id    bigint generated always as identity
        primary key,
    item_name       text  not null,
    menu_item_price money not null
);


create table order_item
(
    order_item_id bigint generated always as identity
        primary key,
    order_id      bigint not null
        references "order",
    menu_item_id  bigint
        references menu_item
);


grant ALL PRIVILEGES on order_item to public;

grant ALL PRIVILEGES on menu_item to public;

create table order_item_product
(
    order_item_order_item_id bigint not null
        references order_item,
    product_product_id       bigint not null
        references product
);


grant ALL PRIVILEGES on order_item_product to public;

