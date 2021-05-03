package io.pivotal.services.pivotMart.streams.consumers;

import io.pivotal.gemfire.domain.BeaconRequest;
import io.pivotal.services.pivotMart.streams.RetailStreamAnalyticsService;

import java.util.function.Consumer;

public class BeaconRequestConsumer implements Consumer<BeaconRequest>
{
    private final RetailStreamAnalyticsService service;

    public BeaconRequestConsumer(RetailStreamAnalyticsService service)
    {
        this.service = service;
    }

    @Override
    public void accept(BeaconRequest beaconRequest)
    {
        service.processBeaconRequest(beaconRequest);
    }
}
