package io.pivotal.market.api.order;

import io.pivotal.gemfire.domain.CustomerFavorites;
import io.pivotal.gemfire.domain.CustomerIdentifier;
import io.pivotal.gemfire.domain.OrderDTO;
import io.pivotal.gemfire.domain.ProductAssociate;
import io.pivotal.market.api.customer.CustomerDao;
import nyla.solutions.core.io.IO;
import org.apache.geode.cache.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderMgmtTest
{
    private OrderMgmt subject;

    @Mock
    private OrderJdbcDAO dao;

    @Mock
    private Region<String, Set<CustomerFavorites>> customerFavoritesRegion;

    @Mock
    private Region<Integer, Set<ProductAssociate>> productAssociationsRegion;

    @Mock
    private CustomerDao customerFavoritesDao;

    @BeforeEach
    public void setup()
    {
        subject = new OrderMgmt(dao, customerFavoritesRegion,  productAssociationsRegion, customerFavoritesDao);


        OrderDTO order = new OrderDTO();
        order.setCustomerIdentifier(new CustomerIdentifier());
        order.getCustomerIdentifier().setFirstName("nyla");
        order.getCustomerIdentifier().setLastName("nyla");

        Integer[] productIds = {1};

        order.setProductIds(productIds);
    }
    @Test
    public void testProcessCSV()
    {
        var csv ="\"0\",\"Nyla\",\"Nyla\",Email,\"77-777\",\"1,2\"";

        var orders = subject.processOrderCSV(csv);

        assertTrue(orders !=null && !orders.isEmpty());

        OrderDTO order = orders.iterator().next();

        Integer [] expected = {1,2};

        assertEquals(Arrays.asList(expected), Arrays.asList(order.getProductIds()));

    }//------------------------------------------------
    @Test
    public void test_multiple_lines()
    throws Exception
    {
        var csv = IO.readFile("src/test/resources/test.csv");

        assertTrue(csv != null && csv.trim().length() > 0,"csv:"+csv);

        var orders = subject.processOrderCSV(csv);

        assertTrue(orders !=null && !orders.isEmpty());

        assertEquals(2, orders.size());

        OrderDTO order = orders.iterator().next();

        Integer [] expected = {1,2,3};

        assertEquals(Arrays.asList(expected), Arrays.asList(order.getProductIds()));

    }


}