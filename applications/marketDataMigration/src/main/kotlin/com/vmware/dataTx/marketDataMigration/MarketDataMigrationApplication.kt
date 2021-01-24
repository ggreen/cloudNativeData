package com.vmware.dataTx.marketDataMigration

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MarketDataMigrationApplication

fun main(args: Array<String>) {
	runApplication<MarketDataMigrationApplication>(*args)
}
