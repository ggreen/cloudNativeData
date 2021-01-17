package io.pivotal.pde.demo.cloudNativeData.services;

import com.google.gson.Gson;
import gedi.solutions.geode.spring.security.SpringSecurityUserService;
import gedi.solutions.geode.spring.security.data.UserProfileDetails;
import io.pivotal.gemfire.domain.*;
import io.pivotal.services.dataTx.geode.RegionTemplate;
import io.pivotal.services.dataTx.geode.client.GeodeClient;
import nyla.solutions.core.util.Organizer;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import solutions.nyla.apacheKafka.ApacheKafka;
import io.pivotal.services.dataTx.geode.lucene.GeodeLuceneSearch;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.*;

@RestController
public class ProductShoppingService
{
	
	private String regionName = "/products";
	private String indexName = "productIndex";
	private String defaultField = "productName";
	
	@Autowired
	private SpringSecurityUserService springSecurityUserService;
	
	@Autowired
	ApacheKafka apacheKafka;
	
	@Autowired
	GeodeLuceneSearch search;
	
	@Resource
	Region<String, Collection<Product>> productRecommendationsRegion;
	
	@Resource
	Region<Integer,Set<ProductAssociate>> productAssociationsRegion;
	
	@GetMapping("/searchProducts/{queryString}")
	public Collection<Product> searchProducts(@PathVariable String queryString)
	throws Exception
	{
		
		return search.search(indexName, regionName, queryString, defaultField);
	}
	
	@GetMapping("findProductRecommendations/{productId}")
	public Collection<Product> findProductRecommendations(@PathVariable String productId)
	{
		return this.productRecommendationsRegion.get(productId);
	}//------------------------------------------------

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/order")
	@PreAuthorize("hasRole('ROLE_WRITE')")
	public OrderReview orderProducts(Principal userPrincipal, @RequestBody Integer[] productIds)
	throws Exception
	{
		try
		{
			if(productIds == null || productIds.length == 0)
				throw new IllegalArgumentException("ProductIds required");
			
			List<Integer> productList = Arrays.asList(productIds);
			
			Region<Integer,Product> productsRegion = 
			GeodeClient.connect().getRegion("products");
			
			
			
			Collection<Product> products = new RegionTemplate(productsRegion).getAllValues(productList);
			//Collection<Product> products = productsRegion).(productList);
			
			OrderReview or = new OrderReview();
			or.setProducts(new HashSet<Product>(products));
			
			
			Collection<Set<ProductAssociate>> associations = productAssociationsRegion.getAll(productList).values();
			if(associations != null && !associations.isEmpty())
			{
				
				Collection<ProductAssociate> productAssociations = new HashSet<>(associations.size());
				
				
				Organizer.flatten(associations, productAssociations);
				
				if(!productAssociations.isEmpty())
					or.setProductAssociates(new HashSet<ProductAssociate>(productAssociations));
				
			}
			
			UserProfileDetails user =springSecurityUserService
										.findUserProfileDetailsByUserName(userPrincipal.getName());

			//String key, String firstName, String lastName, String email, String mobileNumber)
			CustomerIdentifier customerIdentifier = new CustomerIdentifier(userPrincipal.getName(), 
			user.getFirstName(), user.getLastName(),user.getEmail(), user.getPhone());
			
			OrderDTO order = new OrderDTO(customerIdentifier, productIds);

			this.apacheKafka.push("orders", null, new Gson().toJson(order));
			
			return or;
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
}
