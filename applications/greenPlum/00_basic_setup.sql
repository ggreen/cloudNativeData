create role retail with password $PASSWORD;
alter role retail login CREATEEXTTABLE;  
create database retail with owner retail;
