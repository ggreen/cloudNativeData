package com.vmware.data.demo.retail.store.domain;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BeaconTest
{
    @Test
    void getKey()
    {
        Beacon subject = JavaBeanGeneratorCreator.of(Beacon.class).create();
        assertEquals(subject.getKey(),subject.getUuid() + "|" + subject.getMajor() + "|" + subject.getMinor());

    }
}
