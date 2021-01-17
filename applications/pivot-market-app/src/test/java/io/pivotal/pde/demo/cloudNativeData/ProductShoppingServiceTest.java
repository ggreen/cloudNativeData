package io.pivotal.pde.demo.cloudNativeData;

import io.pivotal.gemfire.domain.Product;
import io.pivotal.pde.demo.cloudNativeData.services.ProductShoppingService;
import io.pivotal.services.dataTx.geode.client.GeodeClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
public class ProductShoppingServiceTest
{

	static ProductShoppingService service;
	
	@BeforeAll
	public static void setUp()
	{
		GeodeClient client = GeodeClient.connect();
		
		
		service = new ProductShoppingService();
	}
	
	@Test
	public void testSearch()
	throws Exception
	{
		
		String text = "fruit";
		
		Product product = new  Product();
		product.setProductName("fruit");
		product.setCost(BigDecimal.valueOf(20.32));
		product.setPrice(BigDecimal.valueOf(23.232));
		product.setProductId(-1);
		product.setSubCategoryId("Apples");
		
		//service.storeProduct(product);
		
		Collection<Product> collection = service.searchProducts(text);
		assertNotNull(collection);
		
		assertTrue(!collection.isEmpty());
		
		assertTrue(collection.stream()
					.anyMatch(p -> (p.getProductName() != null) 
						? 	p.getProductName().contains("fruit") 
							: false));
	}

}
