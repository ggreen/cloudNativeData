#!/bin/bash

echo "Stopping locator and server..."
gfsh <<!
connect --locator=localhost[10334]
#undeploy --jar=/Users/hshin/Documents/workspace/git/hwshin16/gf-semiconductor/target/semiconductor-0.0.1-SNAPSHOT.jar
shutdown --include-locators=true
!
