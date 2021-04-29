package com.vmware.data.demo.retail.store.services;

import io.pivotal.gemfire.domain.BeaconRequest;
import com.vmware.data.demo.retail.store.customer.CustomerMgr;
import nyla.solutions.core.data.collections.QueueSupplier;
import org.apache.geode.cache.client.ClientCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class MarketAlertServiceTest
{

    private int retryMs = 3;

    @Mock
    private ClientCache cache;

    @Mock
    private CustomerMgr mgr;

    @Mock
    private Principal user;

    private MarketAlertService subject;

    @Mock
    private HttpServletResponse response;

    @Mock
    QueueSupplier<BeaconRequest> messageChannel;

    @BeforeEach
    void setUp()
    {
        subject = new MarketAlertService(retryMs,
                cache,mgr,messageChannel);
    }

    @Test
    void live_alerts() throws IOException
    {
    }

    @Test
    void sendBeaconRequest()
    {
        String beaconId = "";

        subject.sendBeaconRequest(user,beaconId);
        verify(messageChannel).add(any());
    }

    @Test
    void favorites()
    {
    }
}