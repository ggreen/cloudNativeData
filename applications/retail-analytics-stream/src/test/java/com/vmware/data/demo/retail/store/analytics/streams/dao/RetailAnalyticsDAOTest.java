package com.vmware.data.demo.retail.store.analytics.streams.dao;

import com.vmware.data.demo.retail.store.domain.*;
import com.vmware.data.demo.retail.store.api.product.ProductJdbcDao;
import io.pivotal.services.dataTx.geode.io.QuerierService;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import nyla.solutions.core.util.Organizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RetailAnalyticsDAOTest
{
    @Mock
    private ProductJdbcDao pivotMarketPostgreDAO;

    private RetailAnalyticsDAO subject;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private QuerierService querierService;

    @BeforeEach
    public void setUp()
    {
        subject = new RetailAnalyticsDAO(jdbcTemplate, pivotMarketPostgreDAO, querierService);
    }


    @Test
    void queryOrderCount()
    {
        long expected = 3;
        when(jdbcTemplate.queryForObject(anyString(),any(Class.class))).thenReturn(expected);
        assertEquals(expected,subject.queryOrderCount());
    }

    @Test
    public void testCustomerFavorites()
    {
        /*
        query(sql,args,rm);
         */
        Integer expectedCustomerId = 3;
        ProductQuantity expectedPQ = new JavaBeanGeneratorCreator<ProductQuantity>(ProductQuantity.class)
                .randomizeAll().generateNestedAll().create();

        Collection<ProductQuantity> expectedProductQuantity = Organizer.toList(expectedPQ);

        CustomerFavorites expectedCustomer = new JavaBeanGeneratorCreator<CustomerFavorites>(CustomerFavorites.class)
                .randomizeAll().create();

        expectedCustomer.setProductQuanties(expectedProductQuantity);


        List<CustomerFavorites> expectedCustomers = Organizer.toList(expectedCustomer);

        //int customerId = this.jdbcTemplate.queryForObject(sql,Integer.class,firstName,lastName);
        when(jdbcTemplate.queryForObject(anyString(),any(Class.class),anyString(),anyString())).thenReturn(expectedCustomerId);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class),anyInt())).thenReturn(expectedCustomers);


        CustomerIdentifier customer = new CustomerIdentifier();
        customer.setFirstName("Joe");
        customer.setLastName("Smith");
        CustomerFavorites cp = subject.selectCustomerFavorites(customer).iterator().next();
        assertNotNull(cp);

        assertTrue(cp.getProductQuanties() != null && !cp.getProductQuanties().isEmpty());

        assertTrue(cp
                .getProductQuanties()
                .stream()
                .allMatch(p -> p.getProduct() != null && p.getProduct().getProductName() != null && p.getProduct().getProductName().length() > 0));

    }//------------------------------------------------

    @Test
    public void testSelectProductsByBeacon()
    {
        Product expected =  new JavaBeanGeneratorCreator<Product>(Product.class)
                .randomizeAll().create();

        List<Product> list = Organizer.toList(expected);

        when(jdbcTemplate.query(anyString(),any(Object[].class),any(RowMapper.class)))
                .thenReturn(list);

        Beacon beacon = new Beacon();
        int major = -1;
        int minor = -1;
        String uuid = "2";
        beacon.setMajor(major);
        beacon.setMinor(minor);
        beacon.setUuid(uuid);

        Collection<Product> products = subject.selectProductsByBeacon(beacon);
        assertNotNull(products);
        assertTrue(!products.isEmpty());
    }

    @Test
    public void testSelectPromotionsByProduct()
    {
        Promotion expected = new JavaBeanGeneratorCreator<Promotion>(Promotion.class)
                .create();

        expected.setMarketingMessage("Bread");

        List<Promotion> list = Organizer.toList(expected);

        when(jdbcTemplate.query(anyString(),any(RowMapper.class)))
                .thenReturn(list);

        Product product = null;

        Collection<Promotion> promotions = subject.selectPromotionsByProduct(product);

        assertNull(promotions);
        int wonderBreadId = 58;

        product = new Product();
        product.setProductId(wonderBreadId);

        promotions = subject.selectPromotionsByProduct(product);

        assertNotNull(promotions);
        assertTrue(!promotions.isEmpty());

        assertTrue(promotions.stream().allMatch(p -> p.getMarketingMessage().contains("Bread")));
    }

}
