package com.vmware.data.demo.retail.analytics.migration

import nyla.solutions.core.io.IO
import org.springframework.boot.CommandLineRunner
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
public class RetailDataRunner(val loaders: List<TableCsvLoader>,
                              val jdbcTemplate: JdbcTemplate) : CommandLineRunner
{
    override fun run(vararg args: String?) {

        var sql = IO.readClassPath(("schema.sql"));
        if(sql ==null || sql.length == 0)
            sql = IO.readFile("applications/retail-analytics-data-migration/src/main/resources/schema.sql");

        println("SQL: ${sql}");
        jdbcTemplate.execute(sql);


        sql = IO.readClassPath("load_data.sql");
        if(sql ==null || sql.length == 0)
            sql = IO.readFile("applications/retail-analytics-data-migration/src/main/resources/load_data.sql");

        println("SQL: ${sql}");
        jdbcTemplate.execute(sql);

        for (loader : TableCsvLoader in loaders)
        {
            loader.run();
        }

        println("===================sDONE=================")
    }
}