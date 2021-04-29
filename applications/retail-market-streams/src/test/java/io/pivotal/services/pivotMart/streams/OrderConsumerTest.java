package io.pivotal.services.pivotMart.streams;

import com.vmware.data.retail.store.domain.OrderDTO;
import io.pivotal.services.pivotMart.streams.consumers.OrderConsumer;
import io.pivotal.services.pivotMart.streams.service.PivotMartStreamService;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderConsumerTest
{
    private OrderConsumer subject;

    @Mock
    private PivotMartStreamService service;

    @Test
    void checkOrderQueue()
    {

        subject = new OrderConsumer(service);
        OrderDTO orderDTO = JavaBeanGeneratorCreator
                .of(OrderDTO.class).create();

        subject.accept(orderDTO);
        verify(service).loadProductsCache();
        verify(service).processOrder(orderDTO);
    }
}