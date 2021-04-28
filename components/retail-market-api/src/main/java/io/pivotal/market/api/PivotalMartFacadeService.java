package io.pivotal.market.api;

import java.util.Collection;

import com.vmware.data.retail.store.domain.BeaconRequest;
import com.vmware.data.retail.store.domain.OrderDTO;
import com.vmware.data.retail.store.domain.Product;
import org.springframework.stereotype.Service;

@Service
public interface PivotalMartFacadeService
{
	/**
	 * 
	 * @param br the beacon request
	 */
	void processBeaconRequest(BeaconRequest br);
	
	Product getProduct(int productId);
	
	Boolean loadProductsCache();
	
	
	int processOrder(OrderDTO order);

	
	Collection<OrderDTO> processOrderCSV(String csv);

}