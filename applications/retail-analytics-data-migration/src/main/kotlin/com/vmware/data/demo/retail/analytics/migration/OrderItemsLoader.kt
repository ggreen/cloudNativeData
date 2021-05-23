package com.vmware.data.demo.retail.analytics.migration

import nyla.solutions.core.patterns.creational.Creator
import nyla.solutions.core.patterns.jdbc.Sql
import nyla.solutions.core.patterns.jdbc.office.CsvJdbcLoader
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.io.Reader
import java.sql.Connection

//@Component
public class OrderItemsLoader(private val creator: Creator<Connection>
, @Qualifier("orderItemsCsv")private val reader: Reader) {

    private var sql = "INSERT INTO pivotalmarkets.order_items\n" +
            "(itemid, orderid, productid, quantity, productname)\n" +
            "VALUES(cast (?  AS INTEGER), cast (?  AS INTEGER) , cast (?  AS INTEGER), cast (?  AS float8), ?);";

    fun run() {

        load(reader);

    }

    fun load(reader: Reader): Long {
        var loader = CsvJdbcLoader();
        return loader.load(creator,reader,sql,true);
    }
}