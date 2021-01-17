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
  lastUpdate date,
  username text,
  user_nm text
)
distributed randomly;
