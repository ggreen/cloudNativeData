package com.vmware.data.demo.retail.store.orders.stream;

import com.vmware.data.demo.retail.store.domain.CustomerFavorites;
import com.vmware.data.demo.retail.store.domain.Product;
import com.vmware.data.demo.retail.store.domain.ProductAssociate;
import com.vmware.data.demo.retail.store.domain.Promotion;
import io.pivotal.services.dataTx.geode.client.GeodeClient;
import io.pivotal.services.dataTx.geode.io.QuerierService;
import org.apache.geode.cache.Region;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class GeodeConfig
{
    @Bean
    public QuerierService querierService()
    {
        return GeodeClient.connect().getQuerierService();
    }


    @Bean("beaconProductsRegion")
    public Region<String, Set<Product>> beaconProductsRegion()
    {
        return GeodeClient.connect().getRegion("beaconProducts");
    }

    @Bean("customerFavoritesRegion")
    public Region<String, Set<CustomerFavorites>> customerFavoritesRegion()
    {
        return GeodeClient.connect().getRegion("customerFavorites");
    }//------------------------------------------------

    @Bean("beaconPromotionsRegion")
    public Region<String, Set<CustomerFavorites>> beaconPromotionsRegion()
    {
        return GeodeClient.connect().getRegion("beaconPromotions");
    }//------------------------------------------------
    @Bean("customerLocationRegion")
    Region<String,String> customerLocationRegion()
    {
        return GeodeClient.connect().getRegion("customerLocation");
    }

    @Bean("productsRegion")
    public Region<Integer,Product> productsRegion()
    {
        return GeodeClient.connect().getRegion("products");
    }

    @Bean("customerPromotionsRegion")
    public Region<String, Set<Promotion>> customerPromotionsRegion()
    {
        return GeodeClient.connect().getRegion("customerPromotions");
    }

    //Region<Integer, Set<ProductAssociate>> productAssociationsRegion;
    @Bean("productAssociationsRegion")
    public Region<Integer, Set<ProductAssociate>> productAssociationsRegion()
    {
        return GeodeClient.connect().getRegion("productAssociations");
    }
}
