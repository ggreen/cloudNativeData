Setup Greenplum to run in docker


docker run -it -p 5432:5432  ubuntu bash

---------------

git clone https://github.com/greenplum-db/gpdb.git
cd gpdb
Use docker image based on gpdb/src/tools/docker/centos7

docker run -p 5432:5432 -w /home/build/gpdb -v ${PWD}:/home/build/gpdb:cached -it pivotaldata/gpdb-dev:centos7 /bin/bash

Inside docker (Total time to build and run ~ 15-20 min)

- yum install python
- yum install python3
- yum install python-devel 
- yum install python3-devel  

# ORCA is disabled here to keep the instructions simple

- ./configure --enable-debug --with-perl --with-python --with-libxml --disable-orca --prefix=/usr/local/gpdb
- make -j4

# Install Greenplum binaries (to /usr/local/gpdb)
make install


- README.CentOS.bash
- pip install psycopg2
- pip install pgdb

# Create a single node demo cluster with three segments
source /usr/local/gpdb/greenplum_path.sh
make create-demo-cluster
source ./gpAux/gpdemo/gpdemo-env.sh

# Create and use a test database
createdb greenplum
psql -d greenplum


Build docker images

```shell script
mvn spring-boot:build-image
```
