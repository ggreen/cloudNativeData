package io.pivotal.pde.demo.cloudNativeData.customer;

import io.pivotal.gemfire.domain.Promotion;
import org.apache.geode.cache.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerMgrTest
{
	private CustomerMgr mgr = new CustomerMgr();
	
	@BeforeEach
	@SuppressWarnings("unchecked")
	public void setup()
	{
		String expectedBeacon = "beaconId";
		
		mgr.beaconPromotionsRegion = mock(Region.class);
		mgr.customerLocationRegion = mock(Region.class);

		Collection<Promotion> expectedPromotions = Collections.singleton(new Promotion());
		when(mgr.customerLocationRegion.get("imani")).thenReturn(expectedBeacon);
		when(mgr.beaconPromotionsRegion.get(expectedBeacon)).thenReturn(expectedPromotions);

	}

	
	@Test
	public void testGetCustomerLocation()
	{
				
		String userName = "imani";
		
		String beacon = mgr.whereIsCustomer(userName);
		assertNotNull(beacon);
		
		Collection<Promotion> promotions =  mgr.whatArePromotions(beacon);
		
		assertNotNull(promotions);
		
	}//------------------------------------------------
	@Test
	public void testByPromotionsByUser()
	{
		   assertNull(this.mgr.findPromotions("ggreen"));
		   
		   assertNotNull(this.mgr.findPromotions("imani"));
	}//------------------------------------------------
	
	
	@Test
	public void testSaveCustomerAtBeaconId()
	{
		String name = "ggreen";
		String beaconId = "1212";
		
		this.mgr.saveCustomerAtBeaconId(name,beaconId);
	
	}

}
