package io.pivotal.pde.pivotMart.streams;

import static org.junit.Assert.*;

import org.apache.geode.cache.Region;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

import gedi.solutions.geode.client.GeodeClient;
import io.pivotal.gemfire.domain.BeaconRequest;
import io.pivotal.gemfire.domain.CustomerIdentifier;
import nyla.solutions.core.patterns.workthread.ExecutorBoss;
import solutions.nyla.apacheKafka.ApacheKafka;

public class PivotMartStreamServiceTest
{

	private static PivotMartStreamConf conf = new PivotMartStreamConf();
	private static PivotMartStreamService service;
	private static ApacheKafka kafka;
	
	@BeforeClass
	public static void setUp()
	{

		String jdbcUrl  = "jdbc:postgresql://localhost:5432/template1";
		String userName = "gpadmin";
		String password = Config.getProperty("pwd"); 
		service = new PivotMartStreamService();
		service.beaconProductsRegion = conf.beaconProductsRegion();
		service.customerFavoritesRegion = conf.customerFavoritesRegion();
		service.customerPromotionsRegion = conf.customerPromotionsRegion();
		service.dao = conf.dao(conf.dataSource(jdbcUrl, userName, password));
		service.boss = new ExecutorBoss(1);
		
		kafka = ApacheKafka.connect();
		service.orderQueue = conf.orderQueue(kafka);
		
	}
	
	@Test
	public void testFullFlow()
	{
		
		
		BeaconRequest br = new BeaconRequest();
		br.setCustomerId(new CustomerIdentifier());
		br.getCustomerId().setFirstName("Nyla");
		br.getCustomerId().setLastName("Nyla");
		
		br.setDeviceId("6a468e3e-631f-44e6-8620-cc83330ed994");
		br.setUuid(br.getDeviceId());
		br.setMajor(23);
		br.setMinor(1);
		service.processBeaconRequest(br);
		
		Region<String,Object> region = GeodeClient.connect().getRegion("customerPromotions");
		
		assertNotNull(region.get(String.valueOf(br.getCustomerId())));
		
	}
	
	@Test
	public void testFromKakfa() throws Exception
	{
		
		BeaconRequest br = new BeaconRequest();
		br.setCustomerId(new CustomerIdentifier());
		br.getCustomerId().setFirstName("Nyla");
		br.getCustomerId().setLastName("Nyla");
		br.setDeviceId("6a468e3e-631f-44e6-8620-cc83330ed994");
		br.setUuid(br.getDeviceId());
		br.setMajor(23);
		br.setMinor(1);
		Gson gson = new Gson();
		
		kafka.push("beacon", br.getKey(), gson.toJson(br));
		
		Thread.sleep(8000);
		
		assertTrue(service.checkBeaconRequestQueue() > 0);
		
		Thread.sleep(5000);
		
		Region<String,Object> region = GeodeClient.connect().getRegion("customerPromotions");
		
		
		assertNotNull(region.get(String.valueOf(br.getCustomerId())));
		
	}

}
