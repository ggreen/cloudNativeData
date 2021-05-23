package com.vmware.data.demo.retail.analytics.migration

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MarketDataMigrationApplication

fun main(args: Array<String>) {
	runApplication<MarketDataMigrationApplication>(*args)
}
