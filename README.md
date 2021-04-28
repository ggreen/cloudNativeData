# cloudNativeData

## Setup 


k apply -f /Users/Projects/Pivotal/dataTx/IMDG/geode/dataTx-geode-devOps-bash/cloud/k8/geode-k8.yaml

```shell script
 k apply -f /Users/devtools/integration/messaging/rabbit/rabbit-devOps/kubernetes/cluster-rabbit-1node.yml
```

### Database 

k apply -f /Users/Projects/solutions/cloudNativeData/showCase/dev/cloudNativeData/database/cloud/k8/greenplum.yml


## Applications Docker

mvn spring-boot:build-image


## Cloud Deployment Notes

kind load docker-image pivot-market-app:0.0.3




k cp /Users/Projects/solutions/cloudNativeData/showCase/dev/cloudNativeData/components/pivot-market-domain/target/pivot-market-domain-0.0.5-SNAPSHOT.jar  geode-0:/tmp
k cp /Users/Projects/Pivotal/dataTx/IMDG/geode/extensions/dataTx-geode-extensions-core/components/geode-extensions-api/build/libs/dataTx-geode-extensions-core-2.5.0-SNAPSHOT.jar geode-0:/tmp
k cp /Users/Projects/solutions/nyla/nyla-core/build/libs/nyla.solutions.core-1.4.2.jar geode-0:/tmp

kubectl exec --stdin --tty geode-0 -- /bin/bash


gfsh

connect --locator=geode-0[10334]




create lucene index --region=products --name=productIndex --field=productName
create region --name=products --type=PARTITION

deploy --jar=/tmp/nyla.solutions.core-1.4.2.jar
y

deploy --jar=/tmp/dataTx-geode-extensions-core-2.5.0-SNAPSHOT.jar
y

deploy --jar=/tmp/pivot-market-domain-0.0.5-SNAPSHOT.jar
y

create region  --type=PARTITION --name=customerPromotions
create region  --type=PARTITION --name=customerFavorites
create region  --type=PARTITION --name=alerts
create region  --type=PARTITION --name=users
create region  --type=PARTITION --name=beaconProducts
create region  --type=PARTITION --name=beaconPromotions
create region  --type=PARTITION --name=customerLocation
create region  --type=PARTITION --name=productAssociations
create region  --type=PARTITION --name=products


k apply -f  applications/cloud/k8

k port-forward  pivot-market-app 8080:8080


### RabbitMQ

kubectl exec rabbitmq-server-0 -- rabbitmqctl add_user app CHANGE_ME

kubectl exec rabbitmq-server-0 -- rabbitmqctl set_permissions  -p / app ".*" ".*" ".*"

kubectl exec rabbitmq-server-0 -- rabbitmqctl set_user_tags app administrator


k port-forward  rabbitmq-server-0 15672:15672


## Troubleshooting

Add the following environment varible to your computer SHELL/Environment.

When IntellERROR

```
intelliji Could not initialize class com.intellij.pom.java.LanguageLevel
```

Then 
```
JAVA_OPTS="--add-opens java.base/java.util=ALL-UNNAMED"
```