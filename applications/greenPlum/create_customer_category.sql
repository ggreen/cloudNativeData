drop table if exists customerCategory;
create table customerCategory (
  productId text,
  categoryId text,
  promotionId text,
  weight int
)
distributed randomly;
