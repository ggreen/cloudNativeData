import subprocess
import random
from subprocess import Popen,PIPE,STDOUT, call
get_orders='psql -h 18.213.48.32  -d retail -p 6432 -U retail -t -q -c "select orderid  from pivotalmarkets.orders ;"'
proc=Popen(get_orders, shell=True, stdout=PIPE,)
output=proc.communicate()[0]
orders=output.split()
comma = ','
random.seed(1234)
itemid=1000000
for o in orders:
# print 'order', o
  which = random.random()
  if which <= .10 :
    print str(itemid) + comma + str(o) + comma + str(82)+ comma + str(1.0) + comma + "BabyBum Disposible Diapers"
    itemid +=1
    print str(itemid) + comma + str(o) + comma + str(81) + comma + str(1.0) + comma+ "Pivotal Extra Stength Aspirin"
    itemid +=1
  elif which <= .15:
    print str(itemid) + comma + str(o) + comma + str(81) + comma + str(1.0) + comma+ "Pivotal Extra Stength Aspirin"
    itemid +=1
  elif which <=.20:
    print str(itemid) + comma + str(o) + comma + str(82)+ comma + str(1.0) + comma+ "BabyBum Disposible Diapers"
    itemid +=1
