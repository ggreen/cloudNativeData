package com.vmware.data.demo.retail.store.api.product;

import io.pivotal.gemfire.domain.Product;
import com.vmware.data.demo.retail.store.api.order.OrderJdbcDAO;
import nyla.solutions.core.patterns.workthread.ExecutorBoss;
import nyla.solutions.core.util.Organizer;
import org.apache.geode.cache.Region;

import java.util.HashMap;
import java.util.Map;

public class ProductCacheLoader
{
    private final OrderJdbcDAO dao;
    private final Region<Integer,Product> productsRegion;
    private final ExecutorBoss boss = new ExecutorBoss(1);

    public ProductCacheLoader(OrderJdbcDAO dao, Region<Integer, Product> productsRegion)
    {
        this.dao = dao;
        this.productsRegion = productsRegion;
    }


    /**
     * Load all products from database into cache
     */
    public void loadProductsCache()
    {
        var ids = this.dao.selectProductIds();

        var batchSize = 100;
        var pages= Organizer.toPages(ids, batchSize);

                try
                {
                    Map<Integer, Product> batch = new HashMap<>(batchSize);

                    for (int productId : ids)
                    {
                        batch.put(Integer.valueOf(productId),this.dao.selectProduct(productId));


                        if(batch.size() > batchSize)
                        {
                            this.productsRegion.putAll(batch);
                            batch.clear();
                        }
                    }//end for

                    if(!batch.isEmpty())
                    {
                        this.productsRegion.putAll(batch);
                        batch.clear();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

        }

}
