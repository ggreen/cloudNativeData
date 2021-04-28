package com.vmware.data.services.demo.cloudNativeData;

import com.vmware.data.retail.store.domain.OrderDTO;
import com.vmware.data.retail.store.domain.Product;
import com.vmware.data.retail.store.domain.ProductAssociate;
import com.vmware.data.services.demo.cloudNativeData.services.ProductShoppingService;
import com.vmware.dataTx.geode.spring.security.SpringSecurityUserService;
import com.vmware.dataTx.geode.spring.security.data.UserProfileDetails;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductShoppingServiceTest
{
	private ProductShoppingService subject;

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
		subject = new ProductShoppingService(productsRegion, springSecurityUserService, search,
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
		lenient().when(user.getName()).thenReturn(expectedUserName);


		lenient().when(userProfileDetails.getUsername()).thenReturn("expected");
		lenient().when(userProfileDetails.getPassword()).thenReturn("expected");
		lenient().when(userProfileDetails.getEmail()).thenReturn("expected");
		lenient().when(userProfileDetails.getFirstName()).thenReturn("expected");
		lenient().when(userProfileDetails.getLastName()).thenReturn("expected");
		lenient().when(userProfileDetails.getPhone()).thenReturn("expected");

		lenient().when(this.springSecurityUserService
				.findUserProfileDetailsByUserName(anyString()))
				.thenReturn(userProfileDetails);



		Integer[] productIds = {1};
		subject.orderProducts(user,productIds);
		verify(messageChannel).add(any());
	}
}
