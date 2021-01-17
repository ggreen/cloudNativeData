set search_path to pivotalmarkets;
drop table if exists beaconrequest;
create table beaconrequest (
  customerId int,
  deviceId text,
  major int,
  minor int,
  signalPower int
)
distributed randomly;

drop table if exists beaconresponse;
create table beaconresponse (
  customerId int,
  deviceId text,
  major int,
  minor int,
  signalPower int,
  promotionID int,
  marketingMessage text,
  marketingimageurl text
)
distributed randomly;

drop table if exists beacon;
create table beacon  (
  uuid text,
  major int,
  minor int,
  category text
)
distributed randomly;
drop table if exists beacon;
create table beacon  (
  uuid text,
  major int,
  minor int,
  category text
)
distributed randomly;
drop table if exists category;
create table category (
  categoryID int,
  categoryName text,
  subcategoryID int,
  subcategoryname text
)
distributed randomly;
drop table if exists customerCategory;
create table customerCategory (
  productId text,
  categoryId text,
  promotionId text,
  weight int
)
distributed randomly;
drop table if exists customers;
create table customers (
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
  lastUpdate date
)
distributed randomly;
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
drop table if exists product;
create table product (
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
distributed randomly;
drop table if exists promotion;
create table promotion (
  promotionID int,
  startDate date,
  endDate date,
  marketingMessage text,
  marketingimageurl text
)
distributed randomly;

drop table if exists stores;
create table stores  (
  storeID int,
  name text,
  street text,
  city text,
  state text,
  zipcode char(5),
  longitude double precision,
  latitude double precision
)
distributed randomly;
