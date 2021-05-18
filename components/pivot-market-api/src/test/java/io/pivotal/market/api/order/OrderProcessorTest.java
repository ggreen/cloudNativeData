package io.pivotal.market.api.order;

import io.pivotal.gemfire.domain.CustomerFavorites;
import io.pivotal.gemfire.domain.OrderDTO;
import io.pivotal.gemfire.domain.Product;
import io.pivotal.gemfire.domain.ProductAssociate;
import io.pivotal.market.api.customer.CustomerDao;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import nyla.solutions.core.util.Organizer;
import org.apache.geode.cache.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderProcessorTest
{
    private OrderDTO dto;
    private OrderProcessor subject;

    @Mock
    private OrderJdbcDAO dao;

    @Mock
    private Region<Integer, Set<ProductAssociate>> productAssociationsRegion;

    @Mock
    private Region<String,Set<CustomerFavorites>> customerFavoritesRegion;

    private Set<ProductAssociate> productAssociation;
    private ProductAssociate productAssociate;

    private Product product;

    @Mock
    private CustomerDao customerDao;

    @BeforeEach
    void setUp()
    {
        dto = JavaBeanGeneratorCreator
                .of(OrderDTO.class).create();

        product = JavaBeanGeneratorCreator.of(Product.class).create();
        productAssociate = JavaBeanGeneratorCreator.of(ProductAssociate.class).create();
        productAssociation = Organizer.toSet(productAssociate);

        subject = new OrderProcessor(dao,productAssociationsRegion,customerFavoritesRegion, customerDao);
    }



    @Nested
    class ProcessOrder
    {


        @Test
        void processOrder_insert()
        {

            subject.processOrder(dto);

            verify(dao).insertOrder(dto);
            verify(dao,never()).selectProductAssociates(any());
            verify(customerDao,never()).selectCustomerFavorites(any());
            verify(productAssociationsRegion,never()).put(any(),any());
        }

        @Test
        void processOrder_SelectProductAssociation()
        {
            when(dao.insertOrder(any())).thenReturn(Arrays.asList(product));

            subject.processOrder(dto);

            verify(dao).insertOrder(dto);
            verify(dao).selectProductAssociates(any());
        }

        @Test
        void processOrder_selectCustomerFavorites()
        {
            when(dao.insertOrder(any())).thenReturn(Arrays.asList(product));
            when(dao.selectProductAssociates(product)).thenReturn(productAssociation);

            subject.processOrder(dto);
            verify(productAssociationsRegion).put(anyInt(),any());
            verify(customerDao).updateCustomerFavorites();
            verify(customerDao).selectCustomerFavorites(any());
            verify(productAssociationsRegion).put(any(),any());
        }


    }
}