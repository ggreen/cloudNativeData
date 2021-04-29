package com.vmware.data.demo.retail.store;

import io.pivotal.gemfire.domain.CustomerFavorites;
import io.pivotal.gemfire.domain.Product;
import io.pivotal.gemfire.domain.ProductAssociate;
import io.pivotal.gemfire.domain.Promotion;
import io.pivotal.services.dataTx.geode.RegionTemplate;
import io.pivotal.services.dataTx.geode.client.GeodeClient;
import io.pivotal.services.dataTx.geode.lucene.GeodeLuceneSearch;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.pdx.PdxInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Queue;
import java.util.Set;

@Configuration
public class GeodeConfig
{
	 //Region<Integer, Set<ProductAssociate>> productAssociationsRegion;
	 @Bean(name = "productAssociationsRegion")
	 public  Region<Integer, Set<ProductAssociate>> productAssociationsRegion(@Autowired GeodeClient geodeClient)
	 {
	         return geodeClient.getRegion("productAssociations");
	 }//------------------------------------------------

	 //Region<Integer,Product> productsRegion;
	 @Bean(name = "productsRegion")
	 public Region<String,PdxInstance> productsRegion(@Autowired GeodeClient geodeClient)
	 {
	         return geodeClient.getRegion("products");
	 }//------------------------------------------------

	@Bean
	public RegionTemplate productsRegionTemplate(@Qualifier("productsRegion") Region<String,PdxInstance> productsRegions)
	{
		return new RegionTemplate(productsRegions);
	}//------------------------------------------------
	@Bean
	GeodeClient getGeodeClient()
	{

		return GeodeClient.connect();
	}//------------------------------------------------
	@Bean(name = "gemfireCache")
    public ClientCache getGemfireClientCache(@Autowired GeodeClient geodeClient) throws Exception {		
		
		 return geodeClient.getClientCache();
    }//------------------------------------------------
	@Bean(name = "alerts")
	public Region<String,PdxInstance> getAlerts(@Autowired GeodeClient geodeClient)
	{
		return geodeClient.getRegion("alerts");
	}//------------------------------------------------
	@Bean(name = "productRecommendationsRegion")
	public Region<String, Collection<Product>> productRecommendationsRegion(@Autowired GeodeClient geodeClient)
	{
		return geodeClient.getRegion("productRecommendations");
	}//------------------------------------------------
	@Bean(name = "beaconPromotionsRegion")
	public Region<String,Collection<Promotion>> beaconPromotionsRegion(@Autowired GeodeClient geodeClient)
	{
		return geodeClient.getRegion("beaconPromotions");
	}//------------------------------------------------
	
	
	@Bean(name = "customerLocationRegion")
	public Region<String,String> customerLocationRegion(@Autowired GeodeClient geodeClient)
	{
		return geodeClient.getRegion("customerLocation");
	}//------------------------------------------------
	
	@Bean(name = "customerPromotionsRegion")
	public Region<String,Collection<Promotion>> customerPromotions(@Autowired GeodeClient geodeClient)
	{
		return geodeClient.getRegion("customerPromotions");
	}//------------------------------------------------	
	//
	@Bean(name = "customerFavoritesRegion")
	Region<String,Collection<CustomerFavorites>> getCustomerFavoritesRegion(@Autowired GeodeClient geodeClient)
	{
		return geodeClient.getRegion("customerFavorites");
	}//------------------------------------------------
	
	@Bean(name = "liveAlertsQueue")
	public Queue<Collection<Promotion>> getAlertQueue(@Autowired GeodeClient geodeClient)
	{
		return geodeClient.registerCq("liveAlerts", "select * from /customerPromotions");
	}//------------------------------------------------

	@Bean
	public GeodeLuceneSearch geodeLucentSearch()
	{
		return new GeodeLuceneSearch(GeodeClient.connect().getClientCache());
	}
	
}
