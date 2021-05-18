package com.vmware.data.demo.retail.store.api.product;

import io.pivotal.gemfire.domain.Beacon;
import io.pivotal.gemfire.domain.Product;
import io.pivotal.gemfire.domain.ProductAssociate;
import com.vmware.data.demo.retail.store.api.order.OrderJdbcDAO;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductJdbcDaoTest
{
    private  Product product;


    @Mock
    private JdbcTemplate jdbcTemplate;

    private ProductJdbcDao subject;

    @BeforeEach
    void setUp()
    {
        product = JavaBeanGeneratorCreator
                .of(Product.class).create();
        subject = new ProductJdbcDao(jdbcTemplate);
    }

    @Test
    void selectProductsByBeacon()
    {
        Beacon beacon = JavaBeanGeneratorCreator
                .of(Beacon.class).create();

        subject.selectProductsByBeacon(beacon);
    }

    @Test
    void selectPromotionsByProduct()
    {
        subject.selectPromotionsByProduct(product);
        verify(jdbcTemplate).query(anyString(), any(RowMapper.class), any());
    }

    @Test
    void findProductById()
    {
        subject.findProductById(product.getProductId());
        verify(jdbcTemplate).query(anyString(), any(ResultSetExtractor.class));
    }

    @Test
    void selectProductIds()
    {
        List<Integer> expected = Arrays.asList(1);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(expected);
        List<Integer> actual = subject.selectProductIds();

        assertEquals(expected,actual);
    }

    @Test
    void selectProductAssociates()
    {
        ProductAssociate productAssociation = JavaBeanGeneratorCreator.of(ProductAssociate.class).create();
        List<ProductAssociate> expected = Collections.singletonList(productAssociation);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(expected);
        Set<ProductAssociate> actual = subject.selectProductAssociates(product);
        verify(jdbcTemplate).query(anyString(),any(RowMapper.class));
    }
}