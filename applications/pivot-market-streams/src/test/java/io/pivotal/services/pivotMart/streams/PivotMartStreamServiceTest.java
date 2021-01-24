package io.pivotal.services.pivotMart.streams;

import com.google.gson.Gson;
import io.pivotal.gemfire.domain.BeaconRequest;
import io.pivotal.gemfire.domain.CustomerIdentifier;
import io.pivotal.gemfire.domain.OrderDTO;
import io.pivotal.gemfire.domain.Product;
import io.pivotal.services.dataTx.geode.client.GeodeClient;
import io.pivotal.services.pivotMart.streams.dao.PivotMartDAO;
import nyla.solutions.core.io.csv.CsvWriter;
import nyla.solutions.core.patterns.workthread.ExecutorBoss;
import nyla.solutions.core.util.Organizer;
import nyla.solutions.core.util.Text;
import org.apache.geode.cache.Region;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import solutions.nyla.apacheKafka.ApacheKafka;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class PivotMartStreamServiceTest
{
	private static PivotMartStreamService subject;
	private static ApacheKafka kafka;
	private Gson gson = new Gson();



	@SuppressWarnings("unchecked")
	@BeforeAll
	public static void setUp()
	throws Exception
	{

		subject = new PivotMartStreamService();
		subject.productsRegion = mock(Region.class);
		subject.beaconProductsRegion = mock(Region.class);
		subject.customerFavoritesRegion = mock(Region.class);
		subject.customerPromotionsRegion = mock(Region.class);
		subject.orderQueue = mock(BlockingQueue.class);
		
		OrderDTO order = new OrderDTO();
		order.setCustomerIdentifier(new CustomerIdentifier());
		order.getCustomerIdentifier().setFirstName("nyla");
		order.getCustomerIdentifier().setLastName("nyla");
		
		Integer[] productIds = {1};
		
		order.setProductIds(productIds);
		
		String orderGson = new Gson().toJson(order);
		when(subject.orderQueue.take()).thenReturn(orderGson);
		when(subject.orderQueue.take()).thenReturn(orderGson);
		
		subject.dao = mock(PivotMartDAO.class);
		subject.boss = new ExecutorBoss(1);
		subject.beaconRequestQueue = mock(BlockingQueue.class);
		kafka = mock(ApacheKafka.class);
		
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
	
	@Test
	@Disabled
	public void testCheckBeaconRequestQueueKakfa() throws Exception
	{
		
		BeaconRequest br = new BeaconRequest();
		br.setCustomerId(new CustomerIdentifier());
		br.getCustomerId().setFirstName("John");
		br.getCustomerId().setLastName("Smith");

		br.setDeviceId("6a468e3e-631f-44e6-8620-cc83330ed994");
		br.setUuid(br.getDeviceId());
		br.setMajor(23);
		br.setMinor(1);
		
		
		String json = gson.toJson(br);
		System.out.println("json:"+json);
		
		kafka.push("beacon", br.getKey(), json);
		
		Thread.sleep(100);
		
		assertTrue(subject.checkBeaconRequestQueue() > 0);
		
		Thread.sleep(5000);
		
		Region<String,Object> region = GeodeClient.connect().getRegion("customerPromotions");
		
		
		assertNotNull(region.get(String.valueOf(br.getCustomerId())));
		
	}//------------------------------------------------
	@Test
	public void testOrders()
	throws Exception
	{
		Integer productId =  1;
		List<Integer> expectedIds = Organizer.toList(productId);
		when(subject.dao.selectProductIds()).thenReturn(expectedIds);

		Product expectedProduct = new Product();
		Collection<Product> expecteProductList = Organizer.toList(expectedProduct);
		when(subject.dao.insertOrder(any())).thenReturn(expecteProductList);
		int results = subject.checkOrderQueue();

		assertEquals(1,results);
		
	}//------------------------------------------------
}
