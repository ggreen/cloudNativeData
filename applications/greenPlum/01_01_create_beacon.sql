drop table if exists beacon;
create table beacon  (
  uuid text,
  major int,
  minor int,
  category text
)
distributed randomly;
