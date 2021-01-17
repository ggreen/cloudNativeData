drop table if exists promotion;
create table promotion (
  promotionID int,
  productId int,
  startDate date,
  endDate date,
  marketingMessage text,
  marketingimageurl text
)
distributed randomly;
