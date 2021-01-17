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
