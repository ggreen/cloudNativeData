package io.pivotal.services.pivotMart.streams;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

import nyla.solutions.core.util.Organizer;
import org.apache.geode.cache.Region;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;

import gedi.solutions.geode.client.GeodeClient;
import io.pivotal.gemfire.domain.BeaconRequest;
import io.pivotal.gemfire.domain.CustomerIdentifier;
import io.pivotal.gemfire.domain.OrderDTO;
import io.pivotal.services.pivotMart.streams.PivotMartStreamService;
import io.pivotal.services.pivotMart.streams.dao.PivotMartDAO;
import nyla.solutions.core.io.csv.CsvWriter;
import nyla.solutions.core.patterns.workthread.ExecutorBoss;
import nyla.solutions.core.util.Text;
import solutions.nyla.apacheKafka.ApacheKafka;


public class PivotMartStreamServiceTest
{
	private static PivotMartStreamService service;
	private static ApacheKafka kafka;
	private Gson gson = new Gson();
	
	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void setUp()
	throws Exception
	{

		service = new PivotMartStreamService();
		service.beaconProductsRegion = mock(Region.class);
		service.customerFavoritesRegion = mock(Region.class);
		service.customerPromotionsRegion = mock(Region.class);
		service.orderQueue = mock(BlockingQueue.class);
		
		OrderDTO order = new OrderDTO();
		order.setCustomerIdentifier(new CustomerIdentifier());
		order.getCustomerIdentifier().setFirstName("nyla");
		order.getCustomerIdentifier().setLastName("nyla");
		
		Integer[] productIds = {1};
		
		order.setProductIds(productIds);
		
		String orderGson = new Gson().toJson(order);
		when(service.orderQueue.take()).thenReturn(orderGson);
		when(service.orderQueue.take()).thenReturn(orderGson);
		
		service.dao = mock(PivotMartDAO.class);
		service.boss = new ExecutorBoss(1);
		service.beaconRequestQueue = mock(BlockingQueue.class);
		kafka = mock(ApacheKafka.class);
		
	}
	
	@Test
	@Ignore
	public void testFullFlow()
	{
		
		Region<String,Object> region = GeodeClient.connect().getRegion("customerPromotions");
		
		
		BeaconRequest br = new BeaconRequest();
		br.setCustomerId(new CustomerIdentifier());
		br.getCustomerId().setFirstName("John");
		br.getCustomerId().setLastName("Smith");
		br.setUuid("2");
		
		
		region.remove(br.getCustomerId()+"");
		
		service.processBeaconRequest(br);
		
		
		
		assertNotNull(region.get(String.valueOf(br.getCustomerId())));
		
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
			OrderDTO out = service.processOrderCSV(csv);
			
			assertArrayEquals(out.getProductIds(),orderDTO.getProductIds());
		}
		
		
		
	}
	
	@Test
	@Ignore
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
		
		assertTrue(service.checkBeaconRequestQueue() > 0);
		
		Thread.sleep(5000);
		
		Region<String,Object> region = GeodeClient.connect().getRegion("customerPromotions");
		
		
		assertNotNull(region.get(String.valueOf(br.getCustomerId())));
		
	}//------------------------------------------------
	@Test
	@Ignore
	public void testOrders()
	throws Exception
	{
		
		int results = service.checkOrderQueue();
		
		assertTrue(results > 0);
		
	}//------------------------------------------------
}
