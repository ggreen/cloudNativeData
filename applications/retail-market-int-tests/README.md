
	create region --name=beaconProducts --type=PARTITION
	create region --name=customerFavorites --type=PARTITION
	create region --name=customerPromotions --type=PARTITION
	create region --name=alerts --type=PARTITION

#Install GemFire Jars

	mvn install:install-file -Dfile=/devtools/repositories/IMDG/pivotal-gemfire-9.0.1/lib/geode-core-9.0.1.jar   -DartifactId=geode-core -DgroupId=o.pivotal.gemfire  -Dversion=-9.0.1 -Dpackaging=jar
	    
	mvn install:install-file -Dfile=/devtools/repositories/IMDG/pivotal-gemfire-9.0.1/lib/geode-cq-9.0.1.jar   -DartifactId=geode-cq -DgroupId=o.pivotal.gemfire  -Dversion=-9.0.1 -Dpackaging=jar
	
	mvn install:install-file -Dfile=/devtools/repositories/IMDG/pivotal-gemfire-9.0.1/lib/geode-wan-9.0.1.jar   -DartifactId=geode-wan -DgroupId=o.pivotal.gemfire  -Dversion=-9.0.1 -Dpackaging=jar