package com.vmware.data.demo.retail.store.analytics.streams.controller;

import com.google.gson.Gson;
import com.vmware.data.demo.retail.store.domain.CustomerIdentifier;
import com.vmware.data.demo.retail.store.domain.OrderDTO;
import com.vmware.data.demo.retail.store.domain.Product;
import com.vmware.data.demo.retail.store.analytics.streams.dao.RetailAnalyticsDAO;
import nyla.solutions.core.io.csv.CsvWriter;
import nyla.solutions.core.patterns.workthread.ExecutorBoss;
import nyla.solutions.core.util.Organizer;
import nyla.solutions.core.util.Text;
import org.apache.geode.cache.Region;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.*;


public class RetailStreamAnalyticControllerTest
{
	private static RetailStreamAnalyticController subject;
	private Gson gson = new Gson();

	@SuppressWarnings("unchecked")
	@BeforeAll
	public static void setUp()
	throws Exception
	{

		subject = new RetailStreamAnalyticController();
		subject.productsRegion = mock(Region.class);
		subject.beaconProductsRegion = mock(Region.class);
		subject.customerFavoritesRegion = mock(Region.class);
		subject.customerPromotionsRegion = mock(Region.class);

		OrderDTO order = new OrderDTO();
		order.setCustomerIdentifier(new CustomerIdentifier());
		order.getCustomerIdentifier().setFirstName("nyla");
		order.getCustomerIdentifier().setLastName("nyla");
		
		Integer[] productIds = {1};
		
		order.setProductIds(productIds);
		
		String orderGson = new Gson().toJson(order);
		
		subject.dao = mock(RetailAnalyticsDAO.class);
		subject.boss = new ExecutorBoss(1);

	}

	@Test
	void constructProductAssociations()
	{
		Collection<Product> expected = null;

		subject.cacheProductAssociations(expected);

	}

	@Test
	public void testProcessOrderCsv() throws Exception
	{
		OrderDTO orderDTO = new OrderDTO();
		
		for (int i = 0; i < 3; i++)
		{
			orderDTO.setCustomerIdentifier(new CustomerIdentifier());
			orderDTO.getCustomerIdentifier().setFirstName("firstName"+i);
			orderDTO.getCustomerIdentifier().setLastName("lastName"+i);
			orderDTO.getCustomerIdentifier().setKey("key"+i);
			
			//orderDTO.setProductIds((Integer[])Arrays.asList(i).toArray());
			orderDTO.setProductIds(Organizer.toIntegers(Arrays.asList(i).toArray()));
			
			String csv = CsvWriter.toCSV(orderDTO.getCustomerIdentifier().getKey(),
			orderDTO.getCustomerIdentifier().getFirstName(),
			orderDTO.getCustomerIdentifier().getLastName(),
			orderDTO.getCustomerIdentifier().getMobileNumber(),
			Text.mergeArray(",",orderDTO.getProductIds()));
			OrderDTO out = subject.processOrderCSV(csv);
			
			assertArrayEquals(out.getProductIds(),orderDTO.getProductIds());
		}
		
		
		
	}

	public void testOrders()
	throws Exception
	{
		Integer productId =  1;
		List<Integer> expectedIds = Organizer.toList(productId);
		when(subject.dao.selectProductIds()).thenReturn(expectedIds);

		Product expectedProduct = new Product();
		Collection<Product> expecteProductList = Organizer.toList(expectedProduct);
		when(subject.dao.insertOrder(any())).thenReturn(expecteProductList);

	}//------------------------------------------------
}
