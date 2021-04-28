package com.vmware.data.retail.store.domain

import java.util.*

data class Customer(
    var customerId: Int = 0,
    var firstName: String = "",
    var lastName: String = "",
    var address: Address = Address(),
    var primaryNumber: String = "",
    var mobileNumber: String = "",
    var openDate: Date? = null,
    var lastUpdate: Date? = null
)
