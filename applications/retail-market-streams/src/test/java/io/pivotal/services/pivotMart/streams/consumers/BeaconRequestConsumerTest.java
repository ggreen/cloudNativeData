package io.pivotal.services.pivotMart.streams.consumers;

import com.vmware.data.retail.store.domain.BeaconRequest;
import io.pivotal.services.pivotMart.streams.service.PivotMartStreamService;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BeaconRequestConsumerTest
{
    @Mock
    private PivotMartStreamService service;
    @Test
    void accept()
    {

        BeaconRequestConsumer subject = new BeaconRequestConsumer(service);
        BeaconRequest beaconRequest= JavaBeanGeneratorCreator.of(BeaconRequest.class)
                                                             .create();
        subject.accept(beaconRequest);

        verify(service).processBeaconRequest(beaconRequest);
    }
}