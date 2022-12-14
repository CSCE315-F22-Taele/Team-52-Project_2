BEGIN;


CREATE TABLE public.menu_item
(
    menu_item_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    item_name text NOT NULL,
    menu_item_price money NOT NULL,
    menu_item_category_id bigint,
    configurable boolean NOT NULL,
    PRIMARY KEY (menu_item_id)
);

CREATE TABLE public.menu_item_category
(
    menu_item_category_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    menu_item_category_name text,
    PRIMARY KEY (menu_item_category_id)
);

CREATE TABLE public."order"
(
    order_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    order_date date NOT NULL,
    order_total money NOT NULL,
    PRIMARY KEY (order_id)
);

CREATE TABLE public.order_item
(
    order_item_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    order_id bigint NOT NULL,
    menu_item_id bigint,
    PRIMARY KEY (order_item_id)
);

CREATE TABLE public.order_item_product
(
    order_item_order_item_id bigint NOT NULL,
    product_product_id bigint NOT NULL
);

CREATE TABLE public.product
(
    product_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    product_name text NOT NULL,
    quantity_in_stock integer NOT NULL,
    conversion_factor double precision NOT NULL,
    product_type_id bigint NOT NULL,
    PRIMARY KEY (product_id)
);

CREATE TABLE public.product_type
(
    product_type_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    product_type_name text,
    PRIMARY KEY (product_type_id)
);

CREATE TABLE public.shipment
(
    shipment_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    shipment_date date NOT NULL,
    fulfilled boolean NOT NULL,
    PRIMARY KEY (shipment_id)
);

CREATE TABLE public.shipment_product
(
    shipment_shipment_id bigint NOT NULL,
    product_product_id bigint NOT NULL,
    quantity_ordered double precision NOT NULL
);

CREATE TABLE public.menu_item_product
(
    menu_item_menu_item_id bigint NOT NULL,
    product_product_id bigint NOT NULL,
    optional boolean NOT NULL
);

ALTER TABLE public.menu_item
    ADD FOREIGN KEY (menu_item_category_id)
        REFERENCES public.menu_item_category (menu_item_category_id)
        NOT VALID;


ALTER TABLE public.order_item
    ADD FOREIGN KEY (order_id)
        REFERENCES public."order" (order_id)
        NOT VALID;


ALTER TABLE public.order_item_product
    ADD FOREIGN KEY (order_item_order_item_id)
        REFERENCES public.order_item (order_item_id)
        NOT VALID;


ALTER TABLE public.order_item_product
    ADD FOREIGN KEY (product_product_id)
        REFERENCES public.product (product_id)
        NOT VALID;


ALTER TABLE public.shipment_product
    ADD FOREIGN KEY (product_product_id)
        REFERENCES public.product (product_id)
        NOT VALID;


ALTER TABLE public.shipment_product
    ADD FOREIGN KEY (shipment_shipment_id)
        REFERENCES public.shipment (shipment_id)
        NOT VALID;


ALTER TABLE public.product
    ADD FOREIGN KEY (product_type_id)
        REFERENCES public.product_type (product_type_id)
        NOT VALID;


ALTER TABLE public.menu_item_product
    ADD FOREIGN KEY (menu_item_menu_item_id)
        REFERENCES public.menu_item (menu_item_id)
        NOT VALID;


ALTER TABLE public.menu_item_product
    ADD FOREIGN KEY (product_product_id)
        REFERENCES public.product (product_id)
        NOT VALID;

GRANT ALL ON ALL TABLES IN SCHEMA public TO public;

END;