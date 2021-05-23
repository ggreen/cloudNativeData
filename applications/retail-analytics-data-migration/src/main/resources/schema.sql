
drop schema IF EXISTS pivotalmarkets CASCADE;

create schema pivotalmarkets;
--ALTER ROLE gpadmin SET search_path TO pivotalmarkets;

set search_path to pivotalmarkets;

create table pivotalmarkets.product_association (
    id text PRIMARY KEY,
    associations  text
);

create table pivotalmarkets.beaconrequest (
  customerId int,
  deviceId text,
  major int,
  minor int,
  signalPower int
)
;

create table pivotalmarkets.beaconresponse (
  customerId int,
  deviceId text,
  major int,
  minor int,
  signalPower int,
  promotionID int,
  marketingMessage text,
  marketingimageurl text
)
;


create table pivotalmarkets.beacon  (
  uuid text,
  major int,
  minor int,
  category text
)
;

create table pivotalmarkets.category (
  categoryID int,
  categoryName text,
  subcategoryID int,
  subcategoryname text
)
;

create table pivotalmarkets.customerCategory (
  productId text,
  categoryId text,
  promotionId text,
  weight int
)
;
create table pivotalmarkets.customers (
  customerId integer,
  deviceId text,
  firstName text,
  lastName text,
  street text,
  city text,
  state text,
  zipcode char(5),
  mobileNumber text,
  openDate date,
  lastUpdate date,
  username text,
  user_nm text
)
;



create table pivotalmarkets.orders (
  orderid integer,
  customerid integer,
  storeid integer,
  orderdate date
) ;

create table pivotalmarkets.order_items (
  itemid integer,
  orderid integer,
  productid integer,
  quantity float8,
  productname text
) ;

create table pivotalmarkets.product (
  productId int,
  productName text,
  categoryId int,
  subCategoryId int,
  unit numeric default NULL,
  cost numeric default NULL,
  price numeric default NULL,
  startDate date default NULL,
  endDate date default NULL,
  createdDate date default NULL,
  lastUpdatedDate date default NULL
)
;

create table pivotalmarkets.promotion (
  promotionID int,
  productId int,
  startDate date,
  endDate date,
  marketingMessage text,
  marketingimageurl text
);

create table pivotalmarkets.stores  (
  storeID int,
  name text,
  street text,
  city text,
  state text,
  zipcode char(5),
  longitude double precision,
  latitude double precision
);
