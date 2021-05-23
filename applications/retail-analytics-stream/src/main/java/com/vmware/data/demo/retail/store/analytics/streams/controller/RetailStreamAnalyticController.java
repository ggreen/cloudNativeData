package com.vmware.data.demo.retail.store.analytics.streams.controller;

import com.vmware.data.demo.retail.store.domain.*;
import com.vmware.data.demo.retail.store.analytics.streams.dao.RetailAnalyticsDAO;
import nyla.solutions.core.io.csv.CsvReader;
import nyla.solutions.core.patterns.workthread.ExecutorBoss;
import nyla.solutions.core.patterns.workthread.MemorizedQueue;
import nyla.solutions.core.util.Debugger;
import nyla.solutions.core.util.Organizer;
import nyla.solutions.core.util.Text;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.*;

@RestController
public class RetailStreamAnalyticController
{

    @Autowired
    RetailAnalyticsDAO dao;

    @Resource
    Region<String, Set<Product>> beaconProductsRegion;

    @Resource
    Region<String, Set<CustomerFavorites>> customerFavoritesRegion;

    @Resource
    Region<String, Set<Promotion>> customerPromotionsRegion;

    @Resource
    Region<Integer, Product> productsRegion;

//    @Resource(name = "beaconRequestQueue")
//    BlockingQueue<String> beaconRequestQueue;


//    @Resource(name = "orderQueue")
//    BlockingQueue<String> orderQueue;

    @Resource
    Region<String, String> customerLocationRegion;

    @Resource
    Region<String, Set<Promotion>> beaconPromotionsRegion;

    @Resource
    Region<Integer, Set<ProductAssociate>> productAssociationsRegion;

    @Autowired
    ExecutorBoss boss;


    /**
     * @param br the beacon request
     */
    @PostMapping("/processBeaconRequest")
    public void processBeaconRequest(@RequestBody BeaconRequest br)
    {
        try {

            this.loadProductsCache();

            System.out.println("processBeaconRequest:" + br);

            Beacon beacon = new Beacon();
            beacon.setUuid(br.getUuid());
            beacon.setMajor(br.getMajor());
            beacon.setMinor(br.getMinor());

            cacheCustomerFavorites(br.getCustomerId());

            Set<Product> products = dao.selectProductsByBeacon(beacon);

            if (products == null || products.isEmpty())
                return;

            beaconProductsRegion.put(beacon.getUuid(), products);

            Set<Promotion> promotions = new HashSet<>();
            for (Product product : products) {
                System.out.println(" Looking for promotion for product:" + product);
                Set<Promotion> set = dao.selectPromotionsByProduct(product);
                if (set == null || set.isEmpty())
                    continue;

                System.out.println("found promotions:" + set);

                promotions.addAll(set);

            }
            if (promotions.isEmpty())
                return;

            //Add associations
            this.cacheProductAssociations(products);

            //add promotions
            customerPromotionsRegion.put(br.getCustomerId().getKey(), promotions);
            this.beaconPromotionsRegion.put(beacon.getUuid(), promotions);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }//------------------------------------------------

    private void cacheCustomerFavorites(CustomerIdentifier customerIdentifier)
    {
        Set<CustomerFavorites> cf = dao.selectCustomerFavorites(customerIdentifier);
        if (cf == null || cf.isEmpty())
            return;

        this.customerFavoritesRegion.put(customerIdentifier.getKey(), cf);
    }//------------------------------------------------

    @GetMapping("product/{productId}")
    public Product getProduct(@PathVariable int productId)
    {
        return this.dao.selectProduct(productId);
    }//------------------------------------------------


    @GetMapping("/loadProductsCache")
    public Boolean loadProductsCache()
    {
        java.util.List<Integer> ids = this.dao.selectProductIds();

        int batchSize = 100;
        java.util.List<Collection<Integer>> pages = Organizer.toPages(ids, batchSize);

        MemorizedQueue queue = new MemorizedQueue();

        for (Collection<Integer> collection : pages) {
            queue.add(() -> {
                try {
                    Map<Integer, Product> batch = new HashMap<>(batchSize);

                    for (int productId : collection) {
                        batch.put(Integer.valueOf(productId), this.dao.selectProduct(productId));


                        if (batch.size() > batchSize) {
                            this.productsRegion.putAll(batch);
                            batch.clear();
                        }
                    }//end for

                    if (!batch.isEmpty()) {
                        this.productsRegion.putAll(batch);
                        batch.clear();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }

        this.boss.startWorking(queue, true);

        return true;

    }//------------------------------------------------
    public int processOrder(OrderDTO order)
    {
        Debugger.println(this, "process Order %s", order);

        //insert into order_times
        Collection<Product> products = dao.insertOrder(order);

        cacheProductAssociations(products);

        if (products == null || products.isEmpty())
            return 0;


        //calculate
        this.dao.updateCustomerFavorites();

        //populate region
        this.cacheCustomerFavorites(order.getCustomerIdentifier());

        return products.size();
    }

    protected void cacheProductAssociations(Collection<Product> products)
    {
        if (products == null)
            return;

		Set<ProductAssociate> productAssociation = null;
        for (Product product : products) {
            productAssociation = dao.selectProductAssociates(product);

            if (productAssociation == null || productAssociation.isEmpty())
                continue;

            this.productAssociationsRegion.put(product.getProductId(), productAssociation);
        }

    }//------------------------------------------------

//    @Scheduled(fixedDelay = 5000)
//    public int checkBeaconRequestQueue()
//    throws InterruptedException, JsonSyntaxException
//    {
//        String msg = null;
//        Gson gson = new Gson();
//
//        MemorizedQueue q = new MemorizedQueue();
//        int cnt = 0;
//
//        msg = this.beaconRequestQueue.take();
//        cnt++;
//        final BeaconRequest br = gson.fromJson(msg, BeaconRequest.class);
//
//        q.add(() ->
//                {
//                    processBeaconRequest(br);
//                }
//        );
//
//        if (cnt == 0)
//            return 0;
//
//        boolean background = true;
//        boss.startWorking(q, background);
//
//        return cnt;
//    }

    //@StreamListener(Processor.INPUT)
    //@SendTo(Processor.OUTPUT)
    public OrderDTO processOrderCSV(String csv)
    {
        Debugger.println(this, "processing csv:" + csv);

        try {
            List<String> cells = CsvReader.parse(csv);
            int length = cells.size();

            int i = 0;
            String key = length > i ? cells.get(i) : null;
            i++;

            String firstName = length > i ? cells.get(i) : null;
            i++;

            String lastName = length > i ? cells.get(i) : null;
            i++;

            String email = length > i ? cells.get(i) : null;

            String mobileNumber = length > i ? cells.get(i) : null;
            i++;

            String text = length > i ? cells.get(i) : null;

            if (text == null || text.length() == 0) {
                Debugger.printInfo(this, "No product ids provided:" + csv);
                throw new IllegalArgumentException("No product ids provided:" + csv);
            }
            Integer[] productIds = Text.splitRE(text, ",", Integer.class);

            if (productIds == null || productIds.length == 0) {
                throw new IllegalArgumentException("Product ids are null or empty:" + csv);
            }

            CustomerIdentifier customerIdentifier = new CustomerIdentifier(key, firstName, lastName,
                    email, mobileNumber);
            OrderDTO orderDTO = new OrderDTO(customerIdentifier, productIds);

            this.processOrder(orderDTO);

            return orderDTO;
        }
        catch (RuntimeException e) {
            Debugger.printError(e);

            throw e;
        }

    }

}
