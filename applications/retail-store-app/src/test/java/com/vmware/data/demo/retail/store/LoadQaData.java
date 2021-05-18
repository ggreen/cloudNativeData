package com.vmware.data.demo.retail.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import io.pivotal.services.dataTx.geode.client.GeodeClient;
import org.apache.geode.cache.Region;
import com.vmware.data.demo.retail.store.domain.CustomerFavorites;
import com.vmware.data.demo.retail.store.domain.Product;
import com.vmware.data.demo.retail.store.domain.ProductAssociate;
import com.vmware.data.demo.retail.store.domain.ProductQuantity;
import nyla.solutions.core.util.Organizer;

public class LoadQaData
{
	public static void main(String[] args)
	{
		GeodeClient geode = GeodeClient.connect();
		
		
		Region<Integer, Set<ProductAssociate>> productAssociationsRegion = geode.getRegion("productAssociations");

		
		Region<Integer,Product> productRegion = geode.getRegion("products");
		Product product = new Product();
		
		String [] associateProductNames = {"QA associated product #A",
				"QA associated product #B",
				"QA associated product #C"};
		
		ProductAssociate pa;
		
		for (int i = 0; i < 3; i++)
		{
			
			product.setProductId(i+50000);
			product.setProductName("QA Pivotal product #"+i);
			
			productRegion.put(product.getProductId(), product);
			
			pa = new ProductAssociate(associateProductNames, product.getProductName());
			
			Set<ProductAssociate> set = Organizer.toSet(pa);
			
			productAssociationsRegion.put(product.getProductId(), set);

			
		}
		
		Region<String,Object> userRegion = geode.getRegion("users");
		
		Collection<String> userNames = userRegion.keySetOnServer();
		if(userNames != null && !userNames.isEmpty())
		{
			Region<String,Collection<CustomerFavorites>> customerFavoritesRegion = geode.getRegion("customerFavorites");
			CustomerFavorites favorites;
			ProductQuantity pq;
			
			for (String userName : userNames)
			{
				favorites = new CustomerFavorites();
				pq = new ProductQuantity();
				
				pq.setProduct(product);
				favorites.setCustomerId(1003);
				favorites.setProductQuanties(Organizer.toList(pq));
				ArrayList<CustomerFavorites> list = new ArrayList<>();
				list.add(favorites);
				
				customerFavoritesRegion.put(userName, list);
			}
		}
		
		
		//
		
		
		
	}

}
