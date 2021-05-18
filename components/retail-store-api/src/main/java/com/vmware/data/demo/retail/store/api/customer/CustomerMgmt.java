package com.vmware.data.demo.retail.store.api.customer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.vmware.data.demo.retail.store.api.product.ProductCacheLoader;
import org.apache.geode.cache.Region;
import io.pivotal.gemfire.domain.Beacon;
import io.pivotal.gemfire.domain.BeaconRequest;
import io.pivotal.gemfire.domain.CustomerFavorites;
import io.pivotal.gemfire.domain.CustomerIdentifier;
import io.pivotal.gemfire.domain.Product;
import io.pivotal.gemfire.domain.ProductAssociate;
import io.pivotal.gemfire.domain.Promotion;
import com.vmware.data.demo.retail.store.api.order.OrderJdbcDAO;

public class CustomerMgmt
{

	private final OrderJdbcDAO dao;

	private final Region<String,Set<Product>> beaconProductsRegion;

	private final Region<String,Set<CustomerFavorites>> customerFavoritesRegion;

	private final Region<String,Set<Promotion>> customerPromotionsRegion;


	private final Region<String,Set<Promotion>> beaconPromotionsRegion;

	private final Region<Integer, Set<ProductAssociate>> productAssociationsRegion;

	private final ProductCacheLoader cacheLoader;

	private final CustomerDao customerDao;

	public CustomerMgmt(OrderJdbcDAO dao, Region<String, Set<Product>> beaconProductsRegion, Region<String,
			Set<CustomerFavorites>> customerFavoritesRegion, Region<String, Set<Promotion>> customerPromotionsRegion,
						Region<String, Set<Promotion>> beaconPromotionsRegion,
						Region<Integer, Set<ProductAssociate>> productAssociationsRegion, ProductCacheLoader cacheLoader, CustomerDao customerDao)
	{
		this.dao = dao;
		this.beaconProductsRegion = beaconProductsRegion;
		this.customerFavoritesRegion = customerFavoritesRegion;
		this.customerPromotionsRegion = customerPromotionsRegion;
		this.beaconPromotionsRegion = beaconPromotionsRegion;
		this.productAssociationsRegion = productAssociationsRegion;
		this.cacheLoader = cacheLoader;
		this.customerDao = customerDao;
	}


	/* (non-Javadoc)
	 * @see io.pivotal.market.api.PivotalMartFacadeService#processBeaconRequest(io.pivotal.gemfire.domain.BeaconRequest)
	 */	
	public void processBeaconRequest(BeaconRequest br)
	{
		try
		{
			cacheLoader.loadProductsCache();
			
			System.out.println("processBeaconRequest:"+br);
			
			Beacon beacon = new Beacon();
			beacon.setUuid(br.getUuid());
			beacon.setMajor(br.getMajor());
			beacon.setMinor(br.getMinor());
			
			cacheCustomerFavorites(br.getCustomerId());
			
			Set<Product> products = dao.selectProductsByBeacon(beacon);
			
			if(products == null || products.isEmpty())
				return;
			
			beaconProductsRegion.put(beacon.getKey(), products);
			
			Set<Promotion> promotions = new HashSet<>();
			for (Product product : products)
			{
				System.out.println(" Looking for promotion for product:"+product);
				Set<Promotion> set = dao.selectPromotionsByProduct(product);
				if(set == null || set.isEmpty())
					continue;
				
				System.out.println("found promotions:"+set);
				
				promotions.addAll(set);
				
			}
			if(promotions.isEmpty())
				return;
			
			//Add associations
			this.constructProductAssociations(products);
			
			//add promotions
			customerPromotionsRegion.put(br.getCustomerId().getKey(),promotions);
			this.beaconPromotionsRegion.put(beacon.getKey(),promotions);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
			throw e;
		}
	}//------------------------------------------------

	private void cacheCustomerFavorites(CustomerIdentifier customerIdentifier)
	{
		Set<CustomerFavorites> cf = customerDao.selectCustomerFavorites(customerIdentifier);
		this.customerFavoritesRegion.put(customerIdentifier.getKey(), cf);
	}//------------------------------------------------


	private void constructProductAssociations(Collection<Product> products)
	{

		if(products != null)
		{
			for (Product product : products)
			{				
				Set<ProductAssociate> productAssociation = dao.selectProductAssociates(product);
				
				if(productAssociation == null || productAssociation.isEmpty())
					continue;
				
				this.productAssociationsRegion.put(product.getProductId(),productAssociation);
			}
		}
	}//------------------------------------------------
}
