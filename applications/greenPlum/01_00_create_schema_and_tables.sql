drop schema if exists pivotalmarkets cascade;
create schema pivotalmarkets;
set search_path to pivotalmarkets;
\i create_beacon_request.sql
\i create_beacon_response.sql
\i create_beacon.sql
\i create_category.sql
\i create_customer_category.sql
\i create_customers.sql
\i create_order.sql
\i create_product.sql
\i create_promotion.sql
\i create_stores.sql
