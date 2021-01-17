# use the following convention for beacons:
#  the UUID is the same for all beacons in Pivotal Market Stores
#  the major is the storeid
#  the minor is the aisle -- corresponding to the category
#  the numbering convention
#    0=entrance, 
#    1=beverage 2=product, 3=dairy, 4=frozen, 5=paper goods, #6=meat
#    7=deli, 8=bakery, 9=condiments, 10=canned goods, 11=health and beauty aids
#     99=checkout
uuid='6a468e3e-631f-44e6-8620-cc83330ed994'
major=range(1,101)
minor=[0,1,2,3,4,5,6,7,8,9,10,11,99]
sep = ","
print "uuid,major,minor,category"
category=['entrance', 'beverage','product', 'dairy', 'frozen', 'paper goods', 'meat', 'deli', 'bakery', 'condiments', 'canned goods', 'health and beauty aids', 'checkout']
for maj in major:
  for min in minor:
    cindx = min
    if min == 99:
      cindx=12
    print uuid+sep+str(maj)+sep+str(min)+sep+category[cindx]

