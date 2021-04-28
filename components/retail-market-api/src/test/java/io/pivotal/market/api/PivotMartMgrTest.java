package io.pivotal.market.api;

import com.vmware.data.retail.store.domain.CustomerIdentifier;
import com.vmware.data.retail.store.domain.OrderDTO;
import io.pivotal.market.api.dao.PivotMartDAO;
import nyla.solutions.core.io.IO;
import nyla.solutions.core.patterns.workthread.ExecutorBoss;
import org.apache.geode.cache.Region;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class PivotMartMgrTest
{
	static PivotMartMgr service = null;

	@SuppressWarnings("unchecked")

	@BeforeClass
	public static void setup()
	{
		service = new PivotMartMgr();
		service.beaconProductsRegion = mock(Region.class);
		service.customerFavoritesRegion = mock(Region.class);
		service.customerPromotionsRegion = mock(Region.class);
	
		
		OrderDTO order = new OrderDTO();
		order.setCustomerIdentifier(new CustomerIdentifier());
		order.getCustomerIdentifier().setFirstName("nyla");
		order.getCustomerIdentifier().setLastName("nyla");
		
		Integer[] productIds = {1};
		
		order.setProductIds(productIds);
		
		service.dao = mock(PivotMartDAO.class);
		service.boss = new ExecutorBoss(1);
	}
	@Test
	public void testProcessCSV()
	{
		
		
		String csv ="\"0\",\"Nyla\",\"Nyla\",Email,\"77-777\",\"1,2\"";
		
		Collection<OrderDTO> orders = service.processOrderCSV(csv);
		
		assertTrue(orders !=null && !orders.isEmpty());
		
		OrderDTO order = orders.iterator().next();
		
		Integer [] expected = {1,2};
		
		assertEquals(Arrays.asList(expected), Arrays.asList(order.getProductIds()));
		
	}//------------------------------------------------
	@Test
	public void test_multiple_lines()
	throws Exception
	{
		String csv = IO.readFile("src/test/resources/test.csv");
		
		assertTrue("csv:"+csv,csv != null && csv.trim().length() > 0);
		
		Collection<OrderDTO> orders = service.processOrderCSV(csv);
		
		assertTrue(orders !=null && !orders.isEmpty());
		
		assertEquals(2, orders.size());
		
		OrderDTO order = orders.iterator().next();
		
		Integer [] expected = {1,2,3};
		
		assertEquals(Arrays.asList(expected), Arrays.asList(order.getProductIds()));
		
	}
}
