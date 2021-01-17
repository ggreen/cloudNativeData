drop table if exists beaconrequest;
create table beaconrequest (
  customerId int,
  deviceId text,
  major int,
  minor int,
  signalPower int
)
distributed randomly;
