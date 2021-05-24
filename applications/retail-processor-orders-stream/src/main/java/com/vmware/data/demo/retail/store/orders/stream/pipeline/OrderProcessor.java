package com.vmware.data.demo.retail.store.orders.stream.pipeline;

import com.vmware.data.demo.retail.store.api.order.OrderMgmt;
import com.vmware.data.demo.retail.store.domain.OrderDTO;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.Function;

@Component
public class OrderProcessor implements Function<String,Collection<OrderDTO>>
{

    private final OrderMgmt service;

    public OrderProcessor(OrderMgmt service)
    {
        this.service = service;
    }

    public Collection<OrderDTO> apply(String csv) {

        System.out.println("Stream PROCESSING CSV:"+csv);

        try
        {
            System.out.println("ARGUMENTS:"+System.getProperty("sun.java.command"));
            System.out.println(" csv:"+csv);
            Collection<OrderDTO> order = service.processOrderCSV(csv);

            System.out.println("ORDER processed:"+order);

            return order;
        }
        catch(Exception e)
        {
            System.err.println("CANNOT process csv:"+csv);
            e.printStackTrace();
            throw e;
        }
    }

//    @StreamListener(Processor.INPUT)
//    @SendTo(Processor.OUTPUT)
//    public Collection<OrderDTO> processMessage(Message<String> msg) {
//        return this.apply(msg.getPayload());
//    }//------------------------------------------------
}
