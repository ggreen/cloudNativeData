package io.pivotal.services.pivotMart.streams.consumers;

import com.vmware.data.retail.store.domain.OrderDTO;
import io.pivotal.services.pivotMart.streams.service.PivotMartStreamService;

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
