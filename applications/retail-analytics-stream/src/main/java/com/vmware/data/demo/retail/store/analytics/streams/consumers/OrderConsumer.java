package com.vmware.data.demo.retail.store.analytics.streams.consumers;

import com.vmware.data.demo.retail.store.analytics.streams.RetailStreamAnalyticsService;
import io.pivotal.gemfire.domain.OrderDTO;

import java.util.function.Consumer;

public class OrderConsumer implements Consumer<OrderDTO>
{
    private final RetailStreamAnalyticsService service;
    public OrderConsumer(RetailStreamAnalyticsService service)
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
