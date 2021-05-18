package com.vmware.data.demo.retail.store.api.order;

import com.vmware.data.demo.retail.store.domain.*;
import com.vmware.data.demo.retail.store.api.customer.CustomerDao;
import nyla.solutions.core.util.Debugger;
import org.apache.geode.cache.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class OrderMgmt
{

	private final OrderJdbcDAO orderDao;
	private final Region<String,Set<CustomerFavorites>> customerFavoritesRegion;
	private final Region<Integer, Set<ProductAssociate>> productAssociationsRegion;
	private final CustomerDao customerDao;

	public OrderMgmt(OrderJdbcDAO orderDao, Region<String, Set<CustomerFavorites>> customerFavoritesRegion, Region<Integer,
			Set<ProductAssociate>> productAssociationsRegion, CustomerDao customerDao)
	{
		this.orderDao = orderDao;
		this.customerFavoritesRegion = customerFavoritesRegion;
		this.productAssociationsRegion = productAssociationsRegion;
		this.customerDao = customerDao;
	}


	public int processOrder(OrderDTO order)
	{
		Debugger.println(this,"process Order %s",order);
		
		//insert into order_times
		var products = orderDao.insertOrder(order);
		
		constructProductAssociations(products);
		
		if(products == null || products.isEmpty())
			return 0;
		
		
		//calculate
		
		this.customerDao.updateCustomerFavorites();
		
		//populate region
		this.cacheCustomerFavorites(order.getCustomerIdentifier());
		
		return products.size();
	}

	private void constructProductAssociations(Collection<Product> products)
	{

		if(products != null)
		{
			for (Product product : products)
			{				
				Set<ProductAssociate> productAssociation = orderDao.selectProductAssociates(product);
				
				if(productAssociation == null || productAssociation.isEmpty())
					continue;
				
				this.productAssociationsRegion.put(product.getProductId(),productAssociation);
			}
		}
	}//------------------------------------------------
	public Collection<OrderDTO> processOrderCSV(String csv)
	{
		
		System.out.println("processOrderCSV:"+csv);
		
		if(csv == null || csv.length() == 0)
			return null;
		
		var lines = csv.split("\n");
		
		if(lines == null || lines.length == 0)
			return null;
		
		
		ArrayList<OrderDTO> orders = new ArrayList<>(lines.length);
		
		for (String line : lines)
		{
			line = line.trim();
			if(line.length() ==0)
				continue; //skip empty lines
			
			orders.add(this.processOrderCSVLine(line));
		}
		
		orders.trimToSize();
		
		return orders;
		
	}//------------------------------------------------
	
	/* (non-Javadoc)
	 * @see io.pivotal.market.api.PivotalMartFacadeService#processOrderCSV(java.lang.String)
	 */
	public OrderDTO processOrderCSVLine(String csv)
	{
		Debugger.println(this,"processing csv line:"+csv);
		
		if(csv  == null || csv.length() == 0 || csv.trim().length() == 0)
		{
			Debugger.println(this,"CSV is null. Returning null");
			return null;
		}
		
		try
		{
			var builder = new RetailOrderCsvBuilder();
			builder.buildOrderLine(csv);
			
			var productIds = builder.getProductIds();
			
			if(productIds == null || productIds.length ==0 )
			{
				throw new IllegalArgumentException("Product ids are null or empty:"+csv);
			}
			
			var orderDTO = builder.getOrderDTO();
			
			this.processOrder(orderDTO);
			
			return orderDTO;
		}
		catch (RuntimeException e)
		{
			Debugger.printError(e);
			
			throw e;
		}
		
	}
	private void cacheCustomerFavorites(CustomerIdentifier customerIdentifier)
	{
		var cf = customerDao.selectCustomerFavorites(customerIdentifier);
		this.customerFavoritesRegion.put(customerIdentifier.getKey(), cf);
	}

}
