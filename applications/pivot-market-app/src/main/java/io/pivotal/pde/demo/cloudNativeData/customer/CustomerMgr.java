package io.pivotal.pde.demo.cloudNativeData.customer;

import java.util.Collection;

import javax.annotation.Resource;

import com.vmware.dataTx.geode.spring.security.SpringSecurityUserService;
import com.vmware.dataTx.geode.spring.security.data.UserProfileDetails;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.pivotal.gemfire.domain.CustomerFavorites;
import io.pivotal.gemfire.domain.CustomerIdentifier;
import io.pivotal.gemfire.domain.Promotion;

@Component
public class CustomerMgr
{

	@Resource
	Region<String,Collection<CustomerFavorites>>  customerFavoritesRegion;
	
	@Resource
	Region<String,String> customerLocationRegion;
	
	@Resource
	Region<String,Collection<Promotion>> beaconPromotionsRegion;
	
	@Autowired
	private SpringSecurityUserService springSecurityUserService;
	
	
	Collection<Promotion> whatArePromotions(String beaconKey)
	{
		if(beaconKey == null)
			return null;
		
		return beaconPromotionsRegion.get(beaconKey);
	}//------------------------------------------------
	String whereIsCustomer(String userName)
	{
		if(userName == null)
			return null;
		
		return this.customerLocationRegion.get(userName);
	}//------------------------------------------------
	public Collection<Promotion> findPromotions(String userName)
	{
		if(userName == null)
			return null;
		
		String beaconId = whereIsCustomer(userName);
		if(beaconId == null)
			return null;
		
	
		Collection<Promotion> promotions = whatArePromotions(beaconId);
		
	//if(promotions != null && !promotions.isEmpty())
	//		clearCustomerLocation(userName);
		
		return promotions;
	}//------------------------------------------------
	public void clearCustomerLocation(String userName)
	{
		if(userName == null)
			return;
		
		this.customerLocationRegion.remove(userName);
	}
	public Collection<CustomerFavorites> findFavorites(String userName)
	{
		if(userName == null )
			return null;
		
		return this.customerFavoritesRegion.get(userName);
	}//------------------------------------------------
	public void saveCustomerAtBeaconId(String name, String beaconId)
	{
		this.customerLocationRegion.put(name, beaconId+"|0|0");
	}
	public boolean isAtCheckout(String userName)
	{
		
		return "5|0|0".equals(customerLocationRegion.get(userName));
	}
	public CustomerIdentifier retrieveCustomerIdentifierByUsername(String userName)
	{
		UserProfileDetails upd = springSecurityUserService.findUserProfileDetailsByUserName(userName);
		
		if(upd == null)
			return null;
			
			
		return new CustomerIdentifier(userName, upd.getFirstName(), upd.getLastName(), upd.getEmail(),upd.getPhone());
	}
	
}
