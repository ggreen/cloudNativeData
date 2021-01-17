import random
#
#orders =(838, 181, 894, 344, 812, 405, 276, 663, 583, 869, 672, 887, 501, 991, 983, 937, 165, 622, 336, 535, 919, 288, 254, 885,  54,  64, 167, 569, 925,  80, 268, 341, 951, 557,  42, 706, 378,   8, 441, 533, 442, 572, 223, 444, 128, 475, 829, 989, 884, 998, 471, 129, 411, 835,  35, 602, 102, 213, 173, 870, 382, 361, 806, 918, 948, 807, 519, 975, 628, 782, 685, 472, 138, 302, 203, 660, 133,  60, 392,  30, 595, 931,  89, 582, 851,  15, 724, 130, 162,  99, 751, 182, 623, 781, 749, 729,  24, 976, 616, 914 )
#
orders= list(range(1,6001))
productid = list(range(1,83))
#productid= [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82]
#
productname= ["Pivotal Apple Juice","Ooopsi Cola","Chateau Potomac Water","PBR","Boone Farms Swill","Pivotal Smoothie","pink lady","banana","Kirby cakes","dill","white eggplant","fennel","pink grapefruit","horseradish","Meyer Lemons","Honeydew","napa","california navels","dole pineapple","quince ?","black radish","triple washed spinach","red top turnip","seedless watermelon","ugli fruit","Pivotal 2% Milk","Pivotal Lo-Fat Yoghurt","Land O Lakes Butter","Breakstone Whole Milk Cottage Cheese ","Pivotal Half and Half","Free Range Extra Large","Vermot Extra Sharp Cheddar","Ben and Jerry's Cherry Garcia","Pivotal Small Frozen Peas","Maine Wild Blueberries","Jasper's White Pizza","Uggo Waffles","Organic Vegetarian Sausage","Pivotal Soft and Smooth 24 pack","Husky Paper Towels","12 inch Dinner Plates","Floral Print Napkins","T-bone steak","ground beef","Porky Pig Big Chops","Clucker Farms Organic Chicken","Ball Park Franks","Porky Pig Everloving Bacon","Leg of lamb","Kosher salami","Elmer's Roast Beef","Porky Pig Smoked Ham","Old Zurich Swiss Cheese","Vermonter Mild Cheddar","Mom's Cole Slaw","Mom's Potato Salad","Mom's Deli Mustard","Wonder Bread","Zabar's Old Fahsioned Rye","Organic Whole Wheat Bread","Brooklyn Bagel 6 pack","Donut King 6 pack","Mom's extra frosting birthday cake","Ballpark Hot Dog Rolls","Mr Peanut Peanut Oil","Old Italy Olive Oil","Mom's Old Fashioned Mayo","Heinz Ketchup Quart Bottle","Ball Park Mustard","Homer's Strawberry Jam","Mr Peanut Peanut Butter","K-Town Half Sour Pickles","Happy Valley Sweet Corn","Happy Valley Green Beans","Happy Valley Diced Tomatoes","Happy Valley White Peaches","Pivotal Baked Beans","Jasper's Pizza Sauce", "Devil's Delight Blazing Hot Sausage", "Pivotal Stomach Calmer","Pivotal Extra Strength Aspirin","BabyBum Disposable Diapers"]
#
numprods = len (productid)
itemid = 1
comma = ','
print "itemid,orderid,productid,quantity,productname"
for ord in orders:
   numitems = random.randint(5,25)
#   print "--- orderid= ",ord,"with",  numitems, "items ---"
   for item in range(1,numitems+1):
     itemno = random.randint(1,numprods) - 1
     myprodid = productid[itemno]
     myprodname = productname [itemno]
#     print str(itemid) + comma + str(ord) + comma + str(productid) + comma + productname 
     print itemid , comma , ord , comma , myprodid, comma , str (1.0) , comma, myprodname
     itemid +=1
