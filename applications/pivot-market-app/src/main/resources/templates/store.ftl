<html>
<head>
  <title>Cloud Native Data - Store</title>
  <meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no' />
  <link rel="stylesheet" type="text/css" href="bootstrap.min.css"></link>
  <link rel="stylesheet" type="text/css" href="keen-dashboards.css"></link>
  <link rel="stylesheet" type="text/css" href="main.css"></link>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <script type="text/javascript" src="Alert.js"></script>
  <script type="text/javascript" src="pivotMarket.js"></script>
  <link href="https://d1fto35gcfffzn.cloudfront.net/images/favicon.ico" rel="shortcut icon" type="image/vnd.microsoft.icon"></link>
  <script type="text/javascript">
  		function walk(where)
  		{
  		
  			var storeImg = document.getElementById("storeImg");
  			
  			storeImg.src= "img/supermarket-b"+where+".jpg";
  			
  			sendBeaconRequest(where);
  			
  		}
  </script>
</head>
<body class="application">

	<div id="mainPanel" class="col-sm-9">
      	<br/>
      	<br/>
        <h1>Pivot Mart</h1>
        <br/>
        <div>
        	<strong>Hello ${userName}</strong>
        </div>
        <img id="storeImg" src="img/supermarket-b0.jpg" usemap="#planetmap"/> 
        
        <map name="planetmap">
          <area shape="rect" coords="412,435,518,233" onclick="walk(1);" alt="front"/>
		  <area shape="rect" coords="593,375,758,260" onclick="walk(2);" alt="beverages"/>
		  <area shape="rect" coords="868,353,787,553" onclick="walk(3);" alt="Produce"/>
		  <area shape="rect" coords="625,683,672,452" onclick="walk(4);" alt="Fruit"/>
		  <area shape="rect" coords="446,559,523,391" onclick="walk(5);" alt="Checkout"/>
		
		</map>
      </div>
</body>

</html>
