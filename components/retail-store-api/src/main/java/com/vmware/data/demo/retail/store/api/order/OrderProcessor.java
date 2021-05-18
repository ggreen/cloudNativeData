package com.vmware.data.demo.retail.store.api.order;

import io.pivotal.gemfire.domain.*;
import com.vmware.data.demo.retail.store.api.customer.CustomerDao;
import nyla.solutions.core.util.Debugger;
import org.apache.geode.cache.Region;

import java.util.Collection;
import java.util.Set;

public class OrderProcessor
{
    private final OrderJdbcDAO dao;
    private final Region<Integer, Set<ProductAssociate>> productAssociationsRegion;
    private final Region<String,Set<CustomerFavorites>> customerFavoritesRegion;
    private final CustomerDao customerDao;

    public OrderProcessor(OrderJdbcDAO dao, Region<Integer, Set<ProductAssociate>> productAssociationsRegion,
                          Region<String, Set<CustomerFavorites>> customerFavoritesRegion, CustomerDao customerDao)
    {
        this.dao = dao;
        this.productAssociationsRegion = productAssociationsRegion;
        this.customerFavoritesRegion = customerFavoritesRegion;
        this.customerDao = customerDao;
    }

    public int processOrder(OrderDTO order)
    {
        Debugger.println(this,"process Order %s",order);

        //insert into order_times
        Collection<Product> products = dao.insertOrder(order);

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
                Set<ProductAssociate> productAssociation = dao.selectProductAssociates(product);

                if(productAssociation == null || productAssociation.isEmpty())
                    continue;

                this.productAssociationsRegion.put(product.getProductId(),productAssociation);
            }
        }
    }

    private void cacheCustomerFavorites(CustomerIdentifier customerIdentifier)
    {
        Set<CustomerFavorites> cf = customerDao.selectCustomerFavorites(customerIdentifier);
        this.customerFavoritesRegion.put(customerIdentifier.getKey(), cf);
    }

}
