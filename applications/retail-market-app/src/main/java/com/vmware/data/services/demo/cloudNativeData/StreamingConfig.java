package com.vmware.data.services.demo.cloudNativeData;

import com.vmware.data.retail.store.domain.BeaconRequest;
import com.vmware.data.retail.store.domain.OrderDTO;
import nyla.solutions.core.data.collections.QueueSupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StreamingConfig
{
    @Value("${spring.cloud.function.definition}")
    private String springCloudFunctionDefinition;

    @Bean("beaconRequests")
    QueueSupplier<BeaconRequest> beaconRequestQueue()
    {
        return new QueueSupplier<BeaconRequest>();
    }

    @Bean("orders")
    QueueSupplier<OrderDTO> orderQueue()
    {
        return new QueueSupplier<OrderDTO>();
    }

}
