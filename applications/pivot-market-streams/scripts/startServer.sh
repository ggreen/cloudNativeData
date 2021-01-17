#!/bin/bash

export CLASSPATH=../../target/classes:../../lib/gemfire-greenplum-2.3.0.jar:../../lib/greenplum.5.1.4.jar:../../lib/postgresql-9.4.1212.jar

mkdir -p ../runtime/locator
mkdir -p ../runtime/server1
mkdir -p ../runtime/server2

# Issue commands to gfsh to start locator and launch a server
echo "Starting locator and server..."
gfsh <<!

start locator --name=locator  --dir=../runtime/locator --bind-address=localhost --port=10334  --properties-file=../src/main/resources/locator.properties --include-system-classpath --initial-heap=50m --max-heap=50m --statistic-archive-file=locator.gfs
start server --name=server1 --dir=../runtime/server1 --server-port=40404 --cache-xml-file=server.xml  --properties-file=../src/main/resources/gemfire.properties --include-system-classpath --initial-heap=500m --max-heap=500m --statistic-archive-file=server1.gfs --J-XX:+UseNUMA
start server --name=server2 --dir=../runtime/server2 --server-port=40406 --cache-xml-file=server.xml  --properties-file=../src/main/resources/gemfire.properties --include-system-classpath --initial-heap=500m --max-heap=500m --statistic-archive-file=server2.gfs --J-XX:+UseNUMA

# change the name/ location of this jar file depending on where you launch this script
#deploy --jar=../target/semiconductor-0.0.1-SNAPSHOT.jar

list members;
list regions;
list functions;
!
