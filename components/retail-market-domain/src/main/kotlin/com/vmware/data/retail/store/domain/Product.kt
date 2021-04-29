package com.vmware.data.retail.store.domain

import java.math.BigDecimal
import java.util.*

data class Product(
    var productId: Int = 0,
    var productName: String = "",
    var categoryId: String = "",
    var subCategoryId: String = "",
    var unit: BigDecimal = BigDecimal(0),
    var cost: BigDecimal = BigDecimal(0),
    var price: BigDecimal = BigDecimal(0),
    var startDate: Date? = null,
    var endDate: Date? = null,
    var createdDate: Date? = null,
    var lastUpdatedDate: Date? = null
)
