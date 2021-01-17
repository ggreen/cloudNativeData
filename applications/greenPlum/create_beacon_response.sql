drop table if exists beaconresponse;
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

