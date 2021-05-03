package io.pivotal.services.pivotMart.streams.consumers;

import io.pivotal.gemfire.domain.OrderDTO;
import io.pivotal.services.pivotMart.streams.RetailStreamAnalyticsService;

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
