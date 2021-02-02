package io.pivotal.services.pivotMart.streams.consumers;

import io.pivotal.gemfire.domain.BeaconRequest;
import io.pivotal.services.pivotMart.streams.PivotMartStreamService;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

public class BeaconRequestConsumer implements Consumer<BeaconRequest>
{
    private final PivotMartStreamService service;

    public BeaconRequestConsumer(PivotMartStreamService service)
    {
        this.service = service;
    }

    @Override
    public void accept(BeaconRequest beaconRequest)
    {
        service.processBeaconRequest(beaconRequest);
    }
}
