drop table if exists orders;
create table orders (
  orderid integer,
  customerid integer,
  storeid integer,
  orderdate date
) distributed randomly;

drop table if exists order_items;
create table order_items (
  itemid integer,
  orderid integer,
  productid integer,
  quantity float8,
  productname text
) distributed randomly;
