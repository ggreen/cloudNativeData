#!/bin/bash

#export JAVA_HOME=`/usr/libexec/java_home -v 1.8`
#export GEMFIRE=/usr/local/Cellar/gemfire/9.0.1/libexec
DEMO_HOME=.

export PATH=$PATH:$GEMFIRE/bin
CLASSPATH=$CLASSPATH:$JAVA_HOME/lib/tools.jar
CLASSPATH=$CLASSPATH:$GEMFIRE/lib/geode-core-9.0.1.jar
CLASSPATH=$CLASSPATH:$GEMFIRE/lib/geode-common-9.0.1.jar
CLASSPATH=$CLASSPATH:$GEMFIRE/lib/geode-dependencies.jar
#CLASSPATH=$CLASSPATH:$GEMFIRE/lib/log4j-core-2.6.1.jar
CLASSPATH=$CLASSPATH:$GEMFIRE/lib/antlr-2.7.7.jar
CLASSPATH=$CLASSPATH:$DEMO_HOME/gemfire-greenplum-2.3.0.jar
#CLASSPATH=$CLASSPATH:$DEMO_HOME/g2c-0.0.3-SNAPSHOT.jar
#CLASSPATH=$CLASSPATH:../target/semiconductor-0.0.1-SNAPSHOT.jar
CLASSPATH=$CLASSPATH:../target/classes
export CLASSPATH

echo "Loading data to GF.."
#echo $CLASSPATH
java -classpath $CLASSPATH io.pivotal.gemfire.GfLoader 100000
