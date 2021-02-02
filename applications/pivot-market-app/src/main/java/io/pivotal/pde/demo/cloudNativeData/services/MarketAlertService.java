package io.pivotal.pde.demo.cloudNativeData.services;

import io.pivotal.gemfire.domain.BeaconRequest;
import io.pivotal.gemfire.domain.CustomerFavorites;
import io.pivotal.gemfire.domain.Promotion;
import io.pivotal.pde.demo.cloudNativeData.customer.CustomerMgr;
import nyla.solutions.core.data.collections.QueueSupplier;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Queue;

@RestController
public class MarketAlertService
{

    private final ClientCache gemfireCache;
    private final CustomerMgr customerMgr;
    private final int retryMs;
    private final QueueSupplier<BeaconRequest> messageChannel;

    public MarketAlertService(@Value("${retryMs:900}") int retryMs,
                              ClientCache gemfireCache,
                              CustomerMgr customerMgr,
                              QueueSupplier<BeaconRequest> messageChannel)
    {
        this.retryMs = retryMs;
        this.messageChannel = messageChannel;
        this.gemfireCache = gemfireCache;
        this.customerMgr = customerMgr;
    }


    /**
     * Example Data
     * <p>
     * data: {"tweet": "RT @maggieNYT: The whole \"nationalist vs New York wing\" fight was smart branding for ppl
     * trying to rally troops.. Not accurate, but clever\u2026", "polarity": "0.87"}
     * data: {"tweet": "RT @edko426: @4AllSoulKind @03Ava @cgm807 @zackwack123 @Barbarajean117 @WalkerkillR @nfraizi
     * @GTBighair1 @dynamex @MiceeMouse\u2026 ", "polarity": "0.98"}
     * data: {"tweet": "RT @ChelseaFC: Superb header by Gary Cahill to put us back in front, tremendous bravery by the
     * skipper and wonderful execution. #CHESOU", "polarity": "0.87"}
     * data: {"tweet": "RT @BNightengale: The #Marlins sale of course won't be finalized until #MLB approval. The next
     * owners meeting is next month in New York", "polarity": "0.56"}
     * data: {"tweet": "#3RMXi v0.1.2 had just been released!\nThe Language component State is decoupled from the UI
     * now!\nhttps://t.co/y3D4tIurAf\n#react #javascript", "polarity": "0.67"}
     *
     * @param user     the login user
     * @param response the HTTP response objecdt
     * @throws IOException when IO error occurs
     */
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
