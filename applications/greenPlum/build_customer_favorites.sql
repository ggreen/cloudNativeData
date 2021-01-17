set search_path to pivotalmarkets;

drop table if exists customer_favorites;
create temp table cp1 as
    select o.customerid, i.productid, i.productname
    from orders o, order_items i
    where o.orderid = i.orderid;

create temp table cp2 as
    select customerid, productid, count(*)
    from cp1
    group by productid,customerid
    order by customerid ;

CREATE TABLE customer_favorites as
SELECT customerid ,productid ,count
FROM (
  SELECT *
        , max(count) OVER (PARTITION BY customerid) AS _max_
        , row_number() OVER (PARTITION BY customerid, count ORDER BY random()) AS _rank_  -- include this line to randomly select one if ties unacceptable
  FROM cp2
) foo
WHERE count = _max_
AND _rank_ = 1;

ALTER TABLE customer_favorites ADD COLUMN productname TEXT;
UPDATE customer_favorites c SET productname = (SELECT productname FROM product p WHERE p.productid = c.productid);
