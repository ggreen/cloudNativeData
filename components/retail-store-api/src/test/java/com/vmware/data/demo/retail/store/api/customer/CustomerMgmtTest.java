package com.vmware.data.demo.retail.store.api.customer;

import com.vmware.data.demo.retail.store.domain.*;
import com.vmware.data.demo.retail.store.api.order.OrderJdbcDAO;
import com.vmware.data.demo.retail.store.api.product.ProductCacheLoader;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.apache.geode.cache.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CustomerMgmtTest
{
	static CustomerMgmt subject = null;

	@Mock
	private ProductCacheLoader cacheLoader;
	@Mock
	private OrderJdbcDAO dao;

	@Mock
	private Region<String, Set<Product>> beaconProductsRegion;

	@Mock
	private Region<String, Set<CustomerFavorites>> customerFavoritesRegion;

	@Mock
	private Region<String, Set<Promotion>> beaconPromotionsRegion;

	@Mock
	private Region<Integer, Set<ProductAssociate>> productAssociationsRegion;

	@Mock
	private Region<String, Set<Promotion>> customerPromotionsRegion;

	@Mock
	private CustomerDao customerDao;


	@SuppressWarnings("unchecked")

	@BeforeEach
	public void setup()
	{
		subject = new CustomerMgmt(dao, beaconProductsRegion, customerFavoritesRegion, customerPromotionsRegion,
				beaconPromotionsRegion, productAssociationsRegion, cacheLoader, customerDao);

		OrderDTO order = new OrderDTO();
		order.setCustomerIdentifier(new CustomerIdentifier());
		order.getCustomerIdentifier().setFirstName("nyla");
		order.getCustomerIdentifier().setLastName("nyla");
		
		Integer[] productIds = {1};
		
		order.setProductIds(productIds);

	}

	@Test
	void process()
	{
		BeaconRequest br = JavaBeanGeneratorCreator.of(BeaconRequest.class).create();
		subject.processBeaconRequest(br);

		verify(cacheLoader).loadProductsCache();
		verify(customerDao).selectCustomerFavorites(any());
		verify(customerFavoritesRegion).put(anyString(),any());
		verify(dao).selectProductsByBeacon(any());
	}
}
