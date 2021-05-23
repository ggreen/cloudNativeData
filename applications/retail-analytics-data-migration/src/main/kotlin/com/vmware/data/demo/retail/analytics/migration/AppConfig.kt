package com.vmware.data.demo.retail.analytics.migration

import nyla.solutions.core.io.IO
import nyla.solutions.core.patterns.creational.Creator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.StringReader
import java.sql.Connection
import javax.sql.DataSource

@Configuration
class AppConfig {

    val driverClassName :String? = org.postgresql.Driver::class.qualifiedName;

    class AppConnectionCreator(private val dataSource: DataSource) : Creator<Connection>
    {
        override fun create(): Connection {
            return dataSource.connection;
        }
    }
    @Bean
    fun createConnectionCreator(dataSource : DataSource) : Creator<Connection>
    {
        return AppConnectionCreator(dataSource);
    }

    @Bean
    fun listLoader( connectionCreator: Creator<Connection>) : List<TableCsvLoader>
    {
        val orderItemSql = "INSERT INTO pivotalmarkets.order_items\n" +
                "(itemid, orderid, productid, quantity, productname)\n" +
                "VALUES(cast (?  AS INTEGER), cast (?  AS INTEGER) , cast (?  AS INTEGER), cast (?  AS float8), ?);";

        val orderItems = TableCsvLoader(connectionCreator,
                StringReader(IO.readClassPath("csv/order_items.csv")),
                orderItemSql
        );

        val ordersSql = "INSERT INTO pivotalmarkets.orders\n" +
                "(orderid, customerid, storeid, orderdate)\n" +
                "VALUES(cast(? as integer), cast(? as integer), cast(? as integer), TO_DATE(?,'MM/DD/YYYY'));";

        val orders = TableCsvLoader(connectionCreator,
                StringReader(IO.readClassPath("csv/orders.csv")),
                ordersSql
        );


        //return listOf(orders);
        return listOf(orderItems, orders);
    }



}