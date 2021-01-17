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
