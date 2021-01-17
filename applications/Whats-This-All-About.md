# What's This All About
A colleague at Pivotal used to say that Powerpoint is where Data Science goes to die.  What she meant was that while data analytics can provide interesting insights, unless they're operationalizd, they're of limited balue to the enterprise.
This demo of Cloud Native Data shows a simulation of how data analytics in conjunction with Pivotal Cloud Cache, Spring Cloud Data Flow and Kafka can interoperate to provide real value to the enterpise.
## The Scenario 
There is a chain of supermarkets that has a loyalty program.  Customers get discounts by registering for the program.  
This generates a wealth of data about what they purchase.  One insight we can gain is which items tend to be purchased together which can be done by examining the cash register receipts and using an analytic tool called association rules that determine which pair or triplets or quadruplets of items commonly appear in a market basket.  This information is aggregated over the entire customer base.
We can also determin a particular customer's purcahse pattern and see their most common purchases.

Retail establishtments can place low power Bluetooth beacons in their stores.  When a customer walks near one and the store's loyalty app is active on their phone, the app can detect that the customer has just passed within a very short distance of the beacon.  
The supermarket can place a beacon at the end of each aisle, at the store entrance, at the deli counter, at the checkout counter, etc. and trace the path of the customer through the store.

Merging the assocation rule information and the customer buying pattern and location in the store, 
the app can deliver a location specific message.  
For example, it can issue a welcome message near the store entrance, and a reminder message near the checkout locations.  
It can notice that the customer is a frequent buyer of pizza and notify the customer about a special offer when he or she is on the frozen
food aisle.
It can use the association rules to suggest that if a common pair of items is Spicy Sausage and Extra Strength Stomach Calmer, 
and the customer is a frequent Spicy Sausage buyer, that when the customer approaches the Heath and Beauty Aids aisle, he or she might want to
pick up a bottle of Stomach Calmer.  

## What are the Pivotal Cloud Native Data Products in the demo?

* Pivotal Cloud Foundry (PCF) -- the platfrom on which all the other bits run
* Pivotal Application Service (PAS) -- the control of the application code and its home
* Pivotal Greenplum (GP) -- the analytic database that stores customer data and generates insight into buying behavior
* Pivotal Cloud Cache (PCC) -- where the app gets information from GP about customers it detects are in the store
* Spring Cloud Data Flow  (SCDF) -- orchestrates the flow of data between the app, PCC, and GP 

* Apache Kafka -- a message broker used by SCDF

##  How Does it Work? 
