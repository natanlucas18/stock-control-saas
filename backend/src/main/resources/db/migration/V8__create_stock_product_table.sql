create table stock_product (
    product_id bigint not null,
    stock_location_id bigint not null,
    quantity numeric(10, 3) not null,
    primary key (product_id, stock_location_id)
    );