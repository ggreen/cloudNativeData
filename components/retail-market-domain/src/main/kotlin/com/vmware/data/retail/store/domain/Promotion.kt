package com.vmware.data.retail.store.domain

import java.util.*

data class Promotion(
    var promotionId : Int = 0,
    var startDate: Date? = null,
    var endDate: Date? = null,
    var marketingMessage: String = "",
    var marketingUrl: String = "",
    var productId : Int = 0)