package com.vmware.data.demo.retail.store.controllers;

import com.vmware.data.demo.retail.store.domain.BeaconRequest;
import com.vmware.data.demo.retail.store.domain.CustomerFavorites;
import com.vmware.data.demo.retail.store.domain.Promotion;
import com.vmware.data.demo.retail.store.customer.CustomerMgr;
import nyla.solutions.core.data.collections.QueueSupplier;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;

@RestController
public class RetailStoreAlertController
{

    private final ClientCache gemfireCache;
    private final CustomerMgr customerMgr;
    private final int retryMs;
    private final QueueSupplier<BeaconRequest> messageChannel;

    public RetailStoreAlertController(@Value("${retryMs:900}") int retryMs,
                                      ClientCache gemfireCache,
                                      CustomerMgr customerMgr,
                                      QueueSupplier<BeaconRequest> messageChannel)
    {
        this.retryMs = retryMs;
        this.messageChannel = messageChannel;
        this.gemfireCache = gemfireCache;
        this.customerMgr = customerMgr;
    }


    @CrossOrigin
    @RequestMapping(value = "/live_alerts")
    @ResponseBody
    public void live_alerts(Principal user, HttpServletResponse response)
    throws IOException
    {
        if (user == null)
            return;

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/event-stream");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");

        String userName = user.getName();

        if (this.customerMgr.isAtCheckout(userName)) {
            //TODO: recommendations
            response.getWriter().println("retry: " + retryMs + "\r\n");
            response.getWriter().println(String.format("data: {\"body\": \"%s\"}\r\n", "Next version will send you " +
					"recommendations"));
        }

        Collection<Promotion> promotionCollection = this.customerMgr.findPromotions(userName);

        if (promotionCollection == null)
            return;

        for (Promotion promotion : promotionCollection) {
            System.out.println("pushing promotion:" + promotion);

            response.getWriter().println(String.format("data: {\"body\": \"%s\"}\r\n",
					promotion.getMarketingMessage()));
        }
        response.flushBuffer();

    }//------------------------------------------------

    @GetMapping("/beacon/{beaconId}")
    public void sendBeaconRequest(Principal user, @PathVariable String beaconId)
    {
        if (beaconId == null)
            return;

        this.customerMgr.saveCustomerAtBeaconId(user.getName(), beaconId);

        BeaconRequest beaconRequest = new BeaconRequest();
        beaconRequest.setCustomerId(this.customerMgr.retrieveCustomerIdentifierByUsername(user.getName()));
        beaconRequest.setUuid(beaconId);


        //Push to stream for processing

        this.messageChannel.add(beaconRequest);
    }//------------------------------------------------

    @GetMapping("/favorites")
    public Collection<CustomerFavorites> favorites(Principal user)
    {
        if (user == null)
            return null;

        Collection<CustomerFavorites> favorites = this.customerMgr.findFavorites(user.getName());

        return favorites;

    }//------------------------------------------------

}
