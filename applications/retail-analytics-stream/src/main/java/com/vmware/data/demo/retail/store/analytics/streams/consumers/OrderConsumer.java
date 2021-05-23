package com.vmware.data.demo.retail.store.analytics.streams.consumers;

import com.vmware.data.demo.retail.store.analytics.streams.controller.RetailStreamAnalyticController;
import com.vmware.data.demo.retail.store.domain.OrderDTO;

import java.util.function.Consumer;

public class OrderConsumer implements Consumer<OrderDTO>
{
    private final RetailStreamAnalyticController service;
    public OrderConsumer(RetailStreamAnalyticController service)
    {
        this.service = service;
    }

    @Override
    public void accept(OrderDTO orderDTO)
    {
        service.loadProductsCache();
        service.processOrder(orderDTO);
    }
}
