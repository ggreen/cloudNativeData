package com.vmware.data.retail.store.domain

data class OrderDTO(
    var customerIdentifier: CustomerIdentifier = CustomerIdentifier(),
    var productIds: Array<Int>? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderDTO

        if (customerIdentifier != other.customerIdentifier) return false
        if (!productIds.contentEquals(other.productIds)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = customerIdentifier.hashCode()
        result = 31 * result + productIds.contentHashCode()
        return result
    }
}
