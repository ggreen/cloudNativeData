package com.vmware.data.retail.store.domain

data class CustomerFavorites(
    var productQuanties: Collection<ProductQuantity?>? = null,
    var customerId : Int = 0)
