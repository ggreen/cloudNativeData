package io.pivotal.services.pivotMart.streams.consumers;

import io.pivotal.gemfire.domain.OrderDTO;
import io.pivotal.services.pivotMart.streams.PivotMartStreamService;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

public class OrderConsumer implements Consumer<OrderDTO>
{
    private final PivotMartStreamService service;
    public OrderConsumer(PivotMartStreamService service)
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
