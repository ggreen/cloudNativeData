package io.pivotal.services.pivotMart.streams.consumers;

import com.vmware.data.retail.store.domain.BeaconRequest;
import io.pivotal.services.pivotMart.streams.service.PivotMartStreamService;

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
