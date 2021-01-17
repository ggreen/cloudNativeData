# What's This All About

This project includes a demonstration of caching, data pipeline processing, and analytics for a retail use case. It will showcase the value that technologies such as Pivotal Cloud Cache, Pivotal GemFire, Pivotal Greenplum, Spring Cloud Data Flow, and Pivotal Cloud Foundry bring to a Cloud Native Data Architecture.

## The Scenario

There is a chain of supermarkets that has a loyalty program.  Customers get discounts by registering for the program.  This generates a wealth of data about what they purchase.  One insight we can gain is which items tend to be purchased together which can be done by examining the cash register receipts and using an analytic tool called association rules that determine which pair or triplets or quadruplets of items commonly appear in a market basket.  This information is aggregated over the entire customer base.
We can also determine a particular customer's purchase pattern and see their most common purchases.

Retail establishments can place low power Bluetooth beacons in their stores.  When a customer walks near one and the store's loyalty app is active on their phone, the app can detect that the customer has just passed within a very short distance of the beacon.  
The supermarket can place a beacon at the end of each aisle, at the store entrance, at the deli counter, at the checkout counter, etc. and trace the path of the customer through the store.

Merging the association rule information and the customer buying pattern and location in the store,  the app can deliver a location specific message.  For example, it can issue a welcome message near the store entrance, and a reminder message near the checkout locations.  

It can notice that the customer is a frequent buyer of pizza and notify the customer about a special offer when he or she is on the frozen
food aisle.

It can use the association rules to suggest that if a common pair of items is Spicy Sausage and Extra Strength Stomach Calmer, and the customer is a frequent Spicy Sausage buyer, that when the customer approaches the Heath and Beauty Aids aisle, he or she might want to
pick up a bottle of Stomach Calmer.  

## What are the Pivotal Cloud Native Data Products in the demo?

Cloud Native Data Product | Overview
-------------------------- | ---------------------------------
Pivotal Cloud Foundry (PCF)| the platform on which all the other bits run
Pivotal Application Service (PAS) | the control of the application code and its home
Pivotal Greenplum (GP) | the analytic database that stores customer data and generates insight into buying behavior. It is a Massively Parallel Processing  (MPP) analytic database for petabyte scale data volumes.
Pivotal Cloud Cache (PCC) | where the app gets information from GP about customers it detects are in the store. Pivotal's 12-Factor backing service implementation for GemFire/Apache Geode
Spring Cloud Data Flow  (SCDF) | Orchestrates the flow of data between the app, PCC, and Greenplum. It is Pivotal's Multi-Cloud Spring Cloud Data Flow ESB/ETL based integration platform that runs on Pivotal Cloud Foundry.
Apache Kafka | a message broker used by SCDF

##  How Does it Work?

The first Minimum Viable Product (MVP) version is a small supermarket. There are three product area beacons. There is also one at the entrance and one at the checkout.

[http://pivotmarketapp.apps.pcfone.io](http://pivotmarketapp.apps.pcfone.io)

![Cloud Native Data Demo](https://github.com/Pivotal-Data-Engineering/CloudNativeDataDemo/blob/master/docs/cnatd.jpg?raw=true)

The application is deployed on Pivotal Cloud Foundry (PCF).
Users can open a window to simulate walking through a store.

Clicking on a particular map area will trigger the sending of location information messages
to an Apache Kafka topic. This starts a spring cloud stream asynchronous data flow running in PCF.
The data flow will recalculate updates to the customer's favorite
products based on analytical buying history. The data flow will also  search for promotions of products in the beacon area.

Discovered promotions are cached into Pivotal Cloud Cache (PCC) running in PCF.
PCC notifies the application asynchronously of the discovered promotions.
The application's user interface will display these promotions in near realtime.

## Known Issues

**NOTE:** The current implementation is for a single customer.
The notifications are currently shared across multiple open browsers.
The distribute is shared for a single customer, that ALL open browser instances will receive all messages.
