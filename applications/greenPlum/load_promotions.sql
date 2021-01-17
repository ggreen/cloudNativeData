delete from pivotalmarkets.promotion;

insert into pivotalmarkets.promotion(promotionid,startdate,enddate,marketingmessage,marketingimageurl,productid)
    values
    (1, '2018-08-31'::date, '2019-12-31'::date, '$1 off Pivotal Cranberry', 'https://s3.amazonaws.com/cloud-native-data/canned-cranberry-sauce.jpg',24),
    (2, '2018-09-15'::date, '2019-11-30'::date, 'Save 10% on Happy Valley Organics', 'https://s3.amazonaws.com/cloud-native-data/happy_valley.jpg',22),
    (3, '2018-10-15'::date, '2019-11-25'::date, 'Organic Pizza 25% off', 'https://s3.amazonaws.com/cloud-native-data/pizza.jpg',32),
    (4, '2018-10-15'::date, '2019-11-24'::date, 'Wonder Bread 15% off', 'https://s3.amazonaws.com/cloud-native-data/wonderbread.jpg',30),
    (5, '2018-08-31'::date, '2019-12-31'::date, '$1 off Pivotal Cranberry', 'https://s3.amazonaws.com/cloud-native-data/canned-cranberry-sauce.jpg',1);




    INSERT INTO "pivotalmarkets"."promotion" (promotionid,startdate,enddate,marketingmessage,marketingimageurl,productid)
    VALUES (6,{d '2018-08-31'},{d '2019-12-31'},'Buy get one free apples','TODO',10);


    INSERT INTO "pivotalmarkets"."promotion"
    (promotionid,startdate,enddate,marketingmessage,marketingimageurl,productid)
    VALUES (7,{d '2018-08-31'},{d '2019-12-31'},'$1 off Pivotal Cranberry','https://s3.amazonaws.com/cloud-native-data/canned-cranberry-sauce.jpg',88);
