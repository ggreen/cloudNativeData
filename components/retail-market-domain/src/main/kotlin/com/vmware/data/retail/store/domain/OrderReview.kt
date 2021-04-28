package com.vmware.data.retail.store.domain

data class OrderReview(
    var products: Set<Product?>? = null,
    var productAssociates: Set<ProductAssociate>? = null
)
