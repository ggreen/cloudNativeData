#!/bin/bash

export CLASSPATH=$CLASSPATH:/Users/hshin/Documents/workspace/git/hwshin16/gf-semiconductor/lib/gemfire-greenplum-2.3.0.jar
export CLASSPATH=$CLASSPATH:/Users/hshin/Documents/workspace/git/hwshin16/gf-semiconductor/target/semiconductor-0.0.1-SNAPSHOT.jar

# Issue commands to gfsh to start locator and launch a server
echo "Starting locator and server..."
gfsh <<!
connect

export gpdb --region=/TRACE_GPDB
!
