<html>
<head>
  <title>Cloud Native Data - Store</title>
  <meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no' />
  <link rel="stylesheet" type="text/css" href="bootstrap.min.css"></link>
  <link rel="stylesheet" type="text/css" href="keen-dashboards.css"></link>
  <link rel="stylesheet" type="text/css" href="main.css"></link>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <!-- script type="text/javascript" src="Alert.js"></script-->
  <script type="text/javascript" src="pivotMarket.js"></script>
  <link href="https://d1fto35gcfffzn.cloudfront.net/images/favicon.ico" rel="shortcut icon" type="image/vnd.microsoft.icon"></link>
  <script type="text/javascript">
  		function processUserForm()
  		{
  			
  			var user = {
  					userName: document.getElementById('userName').value,
  					password: document.getElementById('password').value,
  					email: document.getElementById('email').value,
  					firstName: document.getElementById('firstName').value,
  					lastName: document.getElementById('lastName').value,
  					//-----------------------------------------
  					showErrorMsg : function(id,msg) {
  						document.getElementById(id+"Error").innerHTML = msg;
  						return false;
 					},
 					//-----------------------------------------
 					 validate : function(id) {
 						 if(this.userName == null || this.userName.length == 0)
 							return this.showErrorMsg('userName','Required'); 
 						 
 						if(this.password == null || this.password.length == 0)
							return this.showErrorMsg('password','Required'); 
 						
 						
 						if(this.password != document.getElementById('passwordConfirm').value)
							return this.showErrorMsg('password','Passwords does not match'); 
						 
 						if(this.email == null || this.email.length == 0)
							return this.showErrorMsg('email','Required'); 
 						
 						if(this.firstName == null || this.firstName.length == 0)
							return this.showErrorMsg('firstName','Required'); 
 						
 						if(this.lastName == null || this.lastName.length == 0)
							return this.showErrorMsg('lastName','Required');
 						
 						 return true;
 					}
  			};
  			
  			if(!user.validate())
  			{
  				return false;
  			}

  		    $.ajax({
  		        url: 'registerUser',
  		        dataType: 'json',
  		        type: 'POST',
  		        contentType: 'application/json',
  		        data: JSON.stringify(user),
  		        processData: true,
  		        success: function( data, textStatus, jQxhr ){
  		        
  		        var html = "<div>Thank you:"+user.firstName
  		        			+"</div>"
  		        			+"<div>Please login <a href='/'>here</a></div>";
  		        
  		        	
  		          $('#mainPanel').html( html );
  		        	// $('#favorites').html( html );
  		        },
  		        error: function( jqXhr, textStatus, errorThrown ){
  		            alert( jqXhr.responseText );
  		            
  		          document.getElementById("serverError").innerHTML=jqXhr.responseText;
  		        }
  		    });
  			return false;
  		}
  </script>
</head>
<body>
 <div class="pivotalBannerImg"><a href="/"><img  src="img/pivotal.png"/></a></div>
 <h1>Pivot Mart</h1>

  <div>
        	<strong>Loyalty program registration</strong>
  </div>
    <div id="serverError" class="error">
    </div>  
	<div id="mainPanel" class="col-sm-9">
      	<br/>       
       <div>
       <br/>
       <br/>
       <form>
       <table>
	       	<tr>
	       		<td>User Name</td>
	       		<td><input id="userName" name="userName"/></td>
	       		<td> <div id="userNameError" class="error"></div></td>
	       	</tr>
	       	<tr>
	       		<td>Password</td>
	       		<td><input id="password" name="password" type="password"></td>
	       		<td> <div id="passwordError" class="error"></div></td>
	       	</tr>
	       	<tr>
	       		<td>Confirm</td>
	       		<td><input id="passwordConfirm" name="passwordConfirm" type="password"></td>
	       	</tr>
	         <tr>
	       		<td>First Name</td>
	       		<td><input id="firstName" name="firstName" type="text"></td>
	       		<td> <div id="firstNameError" class="error"></div></td>
	       	</tr>
	         <tr>
	       		<td>Last Name</td>
	       		<td><input id="lastName" name="lastName" type="text"></td>
	       		<td> <div id="lastNameError" class="error"></div></td>
	       	</tr>
	       	 <tr>
	       		<td>Email</td>
	       		<td><input id="email" name="email" type="text"></td>
	       		<td> <div id="emailError" class="error"></div></td>
	       	</tr>
	       	

       </table>
       	<br/>
       	<button onclick="processUserForm();return false;">Register</button>
       			
       </form>
      </div>
      </div>
</body>

</html>
