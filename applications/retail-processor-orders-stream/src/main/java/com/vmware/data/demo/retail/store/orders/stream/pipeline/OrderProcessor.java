package com.vmware.data.demo.retail.store.orders.stream.pipeline;

import io.pivotal.gemfire.domain.OrderDTO;
import io.pivotal.market.api.PivotalMartFacadeService;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.Collection;
import java.util.function.Function;

public class OrderProcessor implements Function<String,Collection<OrderDTO>>
{

    private final PivotalMartFacadeService service;

    public OrderProcessor(PivotalMartFacadeService service)
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

    /**
     *
     * @param msg the CSV input msg
     * @return the order details
     */
    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public Collection<OrderDTO> processMessage(Message<String> msg) {
        return this.apply(msg.getPayload());
    }//------------------------------------------------
}
