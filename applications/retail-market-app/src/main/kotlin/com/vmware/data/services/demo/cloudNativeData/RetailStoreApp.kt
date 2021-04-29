package com.vmware.data.services.demo.cloudNativeData

import com.vmware.data.services.demo.cloudNativeData.RetailStoreApp
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RetailStoreApp


    fun main(args: Array<String>) {
        runApplication<RetailStoreApp>(*args)
    }
