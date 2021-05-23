create schema pivotalmarkets;
set search_path to pivotalmarkets;
create table beaconrequest (
  customerId int,
  deviceId text,
  major int,
  minor int,
  signalPower int
)
distributed randomly;

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


create table beacon  (
  uuid text,
  major int,
  minor int,
  category text
)
distributed randomly;

create table category (
  categoryID int,
  categoryName text,
  subcategoryID int,
  subcategoryname text
)
distributed randomly;

create table customerCategory (
  productId text,
  categoryId text,
  promotionId text,
  weight int
)
distributed randomly;

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


create table orders (
  orderid integer,
  customerid integer,
  storeid integer,
  orderdate date
) distributed randomly;

create table order_items (
  itemid integer,
  orderid integer,
  productid integer,
  quantity float8,
  productname text
) distributed randomly;

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

create table promotion (
  promotionID int,
  productId int,
  startDate date,
  endDate date,
  marketingMessage text,
  marketingimageurl text
)
distributed randomly;
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
