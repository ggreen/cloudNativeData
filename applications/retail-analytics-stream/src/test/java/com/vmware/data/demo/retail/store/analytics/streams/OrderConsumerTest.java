package com.vmware.data.demo.retail.store.analytics.streams;

import com.vmware.data.demo.retail.store.analytics.streams.controller.RetailStreamAnalyticController;
import com.vmware.data.demo.retail.store.domain.OrderDTO;
import com.vmware.data.demo.retail.store.analytics.streams.consumers.OrderConsumer;
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
    private RetailStreamAnalyticController service;

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