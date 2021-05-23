package com.vmware.data.demo.retail.store;

import com.vmware.data.demo.retail.store.domain.BeaconRequest;
import com.vmware.data.demo.retail.store.domain.OrderDTO;
import nyla.solutions.core.data.collections.QueueSupplier;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqAmpqStreamingConfig
{
    @Value("${spring.cloud.function.definition:beaconRequests}")
    private String springCloudFunctionDefinition;

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean("beaconRequests")
    public QueueSupplier<BeaconRequest> beaconRequestQueue()
    {
        return new QueueSupplier<BeaconRequest>();
    }

    @Bean("orders")
    public QueueSupplier<OrderDTO> orderQueue()
    {
        return new QueueSupplier<OrderDTO>();
    }

    @Bean
    public ConnectionNameStrategy connectionNameStrategy()
    {
        return (connection) -> applicationName;
    }
}
