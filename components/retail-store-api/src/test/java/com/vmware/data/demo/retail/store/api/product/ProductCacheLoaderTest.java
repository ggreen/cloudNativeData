package com.vmware.data.demo.retail.store.api.product;

import com.vmware.data.demo.retail.store.domain.Product;
import com.vmware.data.demo.retail.store.api.order.OrderJdbcDAO;
import org.apache.geode.cache.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductCacheLoaderTest
{
    @Mock
    private OrderJdbcDAO dao;

    @Mock
    private Region<Integer, Product> productsRegion;

    private ProductCacheLoader subject;

    @BeforeEach
    void setUp()
    {
        subject = new ProductCacheLoader(dao,productsRegion);
    }

    @Test
    public void loadProductsCache()
    {
        List<Integer> ids  = Arrays.asList(1);

        when(dao.selectProductIds()).thenReturn(ids);

        assertDoesNotThrow(() -> subject.loadProductsCache());

        verify(this.dao, times(ids.size())).selectProduct(anyInt());

        verify(productsRegion).putAll(anyMap());

    }

}