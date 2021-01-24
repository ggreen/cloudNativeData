package com.vmware.dataTx.marketDataMigration

import nyla.solutions.core.io.IO
import nyla.solutions.core.patterns.creational.Creator
import nyla.solutions.core.patterns.jdbc.Sql
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.io.StringReader
import java.sql.Connection

internal class OrderItemsLoaderTest{


    @Test
    internal fun load() {
        val driver = "org.h2.Driver";
        val url = "jdbc:h2:mem:test;MODE=PostgreSQL;";
        val user = "junit";
        val password = "junit".toCharArray();
        val connection = Sql.createConnection(driver,url,user,password);
        var sql = Sql();
        val schemaSql = IO.readClassPath("schema.sql");

        sql.execute(connection,schemaSql);

        val creator : Creator<Connection> = mock(Creator::class.java) as Creator<Connection>;
        Mockito.`when`(creator.create()).thenReturn(connection);

        var reader = StringReader(IO.readClassPath("csv/order_items.csv"));
        var subject =  OrderItemsLoader(creator,reader);

        var actual = subject.load(reader);

        val expected = 89470L;
        assertEquals(expected,actual);
    }
}