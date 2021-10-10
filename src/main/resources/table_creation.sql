CREATE TABLE trade_data (
    trade_id integer PRIMARY KEY,
    symbol varchar(10) NOT NULL,
    exchange_code varchar(10) NOT NULL,
    price float(5),
    quantity integer,
    update_time timestamp
);
