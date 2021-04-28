function orderProducts() {
	var orderForm = document.getElementById("orderForm");
	
	var orderIds = "";
	
	for (var i=0; i < orderForm.elements.length; i++) 
    {
    	if(orderForm.elements[i].type == "checkbox" && orderForm.elements[i].checked)
    	{
    		if(orderIds.length !=0 )
    			orderIds += ",";
    		
    		orderIds += orderForm.elements[i].value;
    	}
    }
    
    
	if(orderIds.length ==0 )
	{
		alert("Please select items to order");
		return false;
	}
	
	orderIds = "["+orderIds+"]";
	
	 $.ajax({
	        url: 'order',
	        //dataType: 'json',
	        type: 'post',
	        contentType: 'application/json',
	        data: orderIds,
	        processData: true,
	        success: function( data, textStatus, jQxhr ){
	        	
	        	var html ="<div id='orderSummary'>";
	        	html = "<p>Thank you for your order of the following items</p>";
	        	
	        	html += "<div><table><tr><th>Product Name</th><th>Cost</th></tr>"
	        	
	        	for (var i=0; i < data.products.length; i++) 
	            {
	        		html += "<tr><td>"+data.products[i].productName+"</td>";
	        		html += "<td>"+data.products[i].cost+"</td></tr>";
	        		
	            }
	        	
	        	html += "</table></div>";
	        	html +=  "<br/>"
	        	html += "</div>";
	        	
	        	
	        	//process productAssociates
	        	
	        	if( data.productAssociates != null && data.productAssociates.length > 0)
	        	{
	        		html += "<br/><br/>";
	        		
	        		html += "<h3>Products related to these ordered items</h3>";
	        		
	        		html += "<div><table><tr><th>Purchased Product Name</th><th>Related item</th></tr>"
	        		for (var i=0; i < data.productAssociates.length; i++) 
		            {
	        			html += "<tr><td>"+data.productAssociates[i].pre+"</td>";
	        			html += "<td><ul>";
	        			for (var x=0; x < data.productAssociates[i].post.length; x++) 
			            {
			        		
			        		html +=  "<li>"+data.productAssociates[i].post[x]+"</li>";
			        		
			            }
	        			html += "</ul></td></tr>";
		        		
		            }
	        		html += "</table></div>"; //end product associate table
	        		
	        	}
	        	
	        	 $('#favorites').html( html );
	        },
	        error: function( jqXhr, textStatus, errorThrown ){
	            alert( "ERROR"+errorThrown+" STATUS:"+textStatus );
	        }
	    });
}//-------------------------------------
function searchProducts() {
	
	var productText = document.getElementById("searchText").value;
		
	if(productText == null || productText.length == 0)
			return;
		
	    $.ajax({
        url: 'searchProducts/'+productText,
        //dataType: 'json',
        type: 'get',
        contentType: 'application/json',
        processData: false,
        success: function( data, textStatus, jQxhr ){
        	//alert('data json:'+JSON.stringify(data));
        	
        	
        	
        	//Build HTML
        	/*
        	 * {
				"productId": 27,
				"productName": "Pivotal Low Fat Yoghurt",
				"categoryId": "3",
				"subCategoryId": "3",
				"unit": null,
				"cost": null,
				"price": null,
				"startDate": null,
				"endDate": null,
				"createdDate": null,
				"lastUpdatedDate": null
				},
        	 */
        	
        	if(data == null || data.length == 0)
            {
        		 $('#favorites').html( "<div> No Data found</div>" );
        		 return;
        		 
            }
        	var productArray = data;
        	
        	var html= "<div><button onclick='orderProducts();'>Order</button></</div><form id='orderForm'>";
        		
        	html += "<table><tr><th width='5%'>&nbsp;</th><th width='85%'>Product Name</th><th width='10%'>Cost</th></tr>";
        	
        	for (var i=0; i < productArray.length; i++) { 
        	
               		var product = productArray[i];
               		var price = product.cost;
               		
               		if(price == null)
               			price = "NA";
               		
            		html +="<tr>"+
            			   "<td width='5%'><input type='checkbox' name='productIds' value='"+product.productId+"'/></td>"+
            				"<td width='85%'>"+product.productName+"</td>"+
            				"<td width='10%'>"+price+"</td>"+
            			   "</tr>";
    
            		
        	}
        	html += "</table></form>";
        	
        	//alert("ysodosd"+html);
          // $('#response pre').html( html );
        	 $('#favorites').html( html );
        	 
         	// $('#favorites tr:odd').css("background-color", "#66CDAA");
        },
        error: function( jqXhr, textStatus, errorThrown ){
            alert( errorThrown );
        }
    });
}

function getCustomerFavorites() {
    $.ajax({
        url: 'favorites',
        dataType: 'json',
        type: 'get',
        contentType: 'application/json',
        //data: JSON.stringify({"data":tweets}),
        processData: false,
        success: function( data, textStatus, jQxhr ){
        	//alert('data:'+JSON.stringify(data));
        	
        	//Build HTML
        	var customerFavoritesArray = data;
        	
        	var html= "<div>These seem like your favorites</div><br/><table><tr><th>Product Name</th></tr>";
        	
        	//sort 
        	
        	
        	
        	for (var i=0; i < customerFavoritesArray.length; i++) { 
        	/*
        		customerFavoritesArray[i]
        			.productQuanties
        				.sort(function(a, b)
        						{
        						  return a.productName.localeCompare(b.productName); 
        						}
        				);
        	*/
        		
               	for (var x=0; x < customerFavoritesArray[i].productQuanties.length; x++ ) {
               		
               		var price = customerFavoritesArray[i].productQuanties[x].product.unit;
               		
               		if(price == null)
               			price = "NA";
               		
            		html +="<tr>"+
            				"<td>"+customerFavoritesArray[i].productQuanties[x].product.productName+"</td>"+
            			   "</tr>";
            	}
            		
        	}
        	html += "</table>";
        	
        	//alert("ysodosd"+html);
          // $('#response pre').html( html );
        	 $('#favorites').html( html );
        	 
        	// $('#favorites tr:odd').css("background-color", "#66CDAA");
        	 
        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );
        }
    });
}


function sendBeaconRequest(beaconId) {
    $.ajax({
        url: 'beacon/'+beaconId,
        dataType: 'json',
        type: 'get',
        contentType: 'application/json',
        //data: JSON.stringify({"data":tweets}),
        processData: false,
        success: function( data, textStatus, jQxhr ){
        	//alert('data:'+JSON.stringify(data));
        	
        	//Build HTML
       
        	 $('#favorites').html( html );
        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );
        }
    });
};