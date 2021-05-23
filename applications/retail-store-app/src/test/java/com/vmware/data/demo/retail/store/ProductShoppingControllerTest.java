package com.vmware.data.demo.retail.store;

import com.vmware.data.demo.retail.store.controllers.ProductShoppingController;
import com.vmware.dataTx.geode.spring.security.SpringSecurityUserService;
import com.vmware.dataTx.geode.spring.security.data.UserProfileDetails;
import com.vmware.data.demo.retail.store.domain.OrderDTO;
import com.vmware.data.demo.retail.store.domain.Product;
import com.vmware.data.demo.retail.store.domain.ProductAssociate;
import io.pivotal.services.dataTx.geode.RegionTemplate;
import io.pivotal.services.dataTx.geode.lucene.GeodeLuceneSearch;
import nyla.solutions.core.data.collections.QueueSupplier;
import org.apache.geode.cache.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductShoppingControllerTest
{
	private ProductShoppingController subject;

	@Mock
	private Principal user;

	@Mock
	private QueueSupplier<OrderDTO> messageChannel;

	@Mock
	private RegionTemplate<Integer, Product> productsRegion;

	@Mock
	private SpringSecurityUserService springSecurityUserService;

	@Mock
	private GeodeLuceneSearch search;

	@Mock
	private Region<String, Collection<Product>> productRecommendationsRegion;

	@Mock
	private Region<Integer, Set<ProductAssociate>> productAssociationsRegion;

	@Mock
	private UserProfileDetails userProfileDetails;
	private String expectedUserName = "hello";

	@BeforeEach
	public void setUp()
	{
		subject = new ProductShoppingController(productsRegion, springSecurityUserService, search,
				productRecommendationsRegion, productAssociationsRegion,messageChannel);
	}
	
	@Test
	public void search()
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
		Collection expected = Arrays.asList(product);

		when(search.search(anyString(),anyString(),anyString(),anyString()))
				.thenReturn(expected);
		Collection<Product> actual = subject.searchProducts(text);

		assertEquals(expected,actual);
		

	}

	@Test
	void orderProducts() throws Exception
	{
		when(user.getName()).thenReturn(expectedUserName);

		when(this.springSecurityUserService
				.findUserProfileDetailsByUserName(anyString()))
				.thenReturn(userProfileDetails);

		Integer[] productIds = {1};
		subject.orderProducts(user,productIds);
		verify(messageChannel).add(any());
	}
}
