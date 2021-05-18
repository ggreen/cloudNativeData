package com.vmware.data.demo.retail.store.analytics.streams.consumers;

import com.vmware.data.demo.retail.store.analytics.streams.RetailStreamAnalyticsService;
import com.vmware.data.demo.retail.store.domain.BeaconRequest;

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
