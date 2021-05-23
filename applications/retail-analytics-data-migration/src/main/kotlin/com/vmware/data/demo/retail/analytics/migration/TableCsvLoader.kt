package com.vmware.data.demo.retail.analytics.migration

import nyla.solutions.core.patterns.creational.Creator
import nyla.solutions.core.patterns.jdbc.Sql
import nyla.solutions.core.patterns.jdbc.office.CsvJdbcLoader
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.io.Reader
import java.sql.Connection

public class TableCsvLoader(private val creator: Creator<Connection>,
                            private val reader: Reader,
                            private val sql : String
                            )
{

    fun run() {
        load(reader);
    }
    fun load(reader: Reader): Long {
        var loader = CsvJdbcLoader();
        println("EXECUTING: ${sql}")
        return loader.load(creator,reader,sql,true);
    }
}