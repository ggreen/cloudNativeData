drop table if exists category;
create table category (
  categoryID int,
  categoryName text,
  subcategoryID int,
  subcategoryname text
)
distributed randomly;
