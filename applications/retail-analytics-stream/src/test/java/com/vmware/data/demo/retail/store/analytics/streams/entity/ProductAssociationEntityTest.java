package com.vmware.data.demo.retail.store.analytics.streams.entity;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ProductAssociationEntityTest
{
    @Test
    void retrieveAssociations()
    {
        ProductAssociationEntity subject = new ProductAssociationEntity();
        subject.setAssociations(Arrays.asList("1","2"));

        String expected = "1|2";
        assertEquals(expected,subject.retrieveAssociationsText());
    }

    @Test
    void setRawAssociations()
    {
        String id = "hi";
        String associates = "1|2";
        ProductAssociationEntity subject = new ProductAssociationEntity(id,associates);
        String[] actual = subject.getAssociations();
        assertNotNull(actual);
        assertEquals(2,actual.length);
        assertTrue(Arrays.binarySearch(actual,"1") > -1);
        assertTrue(Arrays.binarySearch(actual,"2") > -1);
        assertEquals(associates,subject.retrieveAssociationsText());


    }
}