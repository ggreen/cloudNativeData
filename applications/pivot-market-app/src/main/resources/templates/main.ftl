<html>
<head>
  <title>Cloud Native Data</title>
  <meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no' />
  <link rel="stylesheet" type="text/css" href="bootstrap.min.css"></link>
  <link rel="stylesheet" type="text/css" href="keen-dashboards.css"></link>
  <link rel="stylesheet" type="text/css" href="main.css"></link>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <script type="text/javascript" src="Alert.js"></script>
  <script type="text/javascript" src="sendSyntheticTweets.js"></script>
  <script type="text/javascript" src="pivotMarket.js"></script>
  <link href="https://d1fto35gcfffzn.cloudfront.net/images/favicon.ico" rel="shortcut icon" type="image/vnd.microsoft.icon"></link>
  <script type="text/javascript">
  		function walk(where)
  		{
  			var storeImg = document.getElementById("storeImg");
  			
  			storeImg.src= "img/supermarket-b"+where+".jpg";
  			
  		}
  		
  		var userName = '${userName}';
  		var customerId = '1003';
  	
  </script>
</head>
<body class="application">

  <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>

        <a class="navbar-brand" href="">Cloud Native Data Demo</a>
      </div>
      <div class="navbar-collapse collapse">
        <ul class="nav navbar-nav navbar-left">
          <li><a href="https://github.com/Pivotal-Data-Engineering/CloudNativeDataDemo">Source (GitHub - Private)</a></li>
          <li><a href="https://pivotal.io/">Pivotal.io</a></li>
          <li><a href="logout"><strong>Logout</strong></a></li>
          
        </ul>
      </div>
    </div>
  </div>

  <div class="container-fluid">
      <div class="col-sm-3">
        <div class="chart-wrapper">
                  <div class="chart-title container-fluid">
                      <div class="col-sm-6" style="font-size:20px">Customer Alerts</div>
                      <div class="col-sm-6" style="font-size:20px"><canvas id="myCanvas" width="120" height="20"></canvas></div>
                  </div>
          <div class="chart-stage" style="height:825px">
            <table class="" id="output">
            </table>
          </div>
          <div class="chart-notes">
          </div>
        </div>
      </div>

      <div id="mainPanel" class="col-sm-9">
   
        <h1>Pivot Mart</h1>
         <div>
        	<strong>Hello ${userName}</strong>  ->  <a href="#" onclick="window.open('store','storeWindwow','menubar=no,resizable=yes,scrollbars=yes,toolbar=no,titlebar=no');return false;">Walk around in STORE</a>
        </div>
        <div>
        	<input id="searchText"  title="Search Products" name="searchText" type="text" 
        	maxlength="1024" size="40" on/>  <img alt="Product Search" id="searchButton" src="img/search.jpg" onclick="searchProducts();">
        </div>
        <br/>
        <p><a href='#' onclick='getCustomerFavorites();return false;'>See Favorites</a></p>
        <div id="favorites">
        </div>
   
      </div>

    <hr/>

  </div>

  <script type="text/javascript" src="holder.js"></script>
  <script>
    Holder.add_theme("white", { background:"#fff", foreground:"#a7a7a7", size:10 });
  </script>
  <script type="text/javascript" src="keen.min.js"></script>
  <script type="text/javascript" src="meta.js"></script>
  <script src="http://d3js.org/d3.v4.min.js"></script>
  <!-- script type="text/javascript" src="tweetChart.js"></script-->
    <script>
      var canvas = document.getElementById('myCanvas');
      var context = canvas.getContext('2d');
      var centerX = canvas.width / 2;
      var centerY = canvas.height / 2;
      var radius = 8;

      context.beginPath();
      context.arc(centerX+50, centerY, radius, 0, 2 * Math.PI, false);
      context.fillStyle = 'green';
      context.fill();
      context.fillStyle = 'black';
      context.font = "14px Arial";
      context.fillText("Feed Active:", 10, 15);
      
      
   // Get the input field
      var searchInput = document.getElementById("searchText");

      // Execute a function when the user releases a key on the keyboard
      searchInput.addEventListener("keyup", function(event) {
        // Cancel the default action, if needed
        event.preventDefault();
        // Number 13 is the "Enter" key on the keyboard
        if (event.keyCode === 13) {
          // Trigger the button element with a click
          document.getElementById("searchButton").click();
        }
      });

        // for tweet stats
//         var sentimentComputeURL = 'SENTIMENT_COMPUTE_URL';
//         var sentimentStatsURL = 'SENTIMENT_STATS_URL';
      
        getCustomerFavorites(1003);

    </script>
</body>

</html>
