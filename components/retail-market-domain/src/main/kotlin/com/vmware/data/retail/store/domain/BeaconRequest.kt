package com.vmware.data.retail.store.domain


data class BeaconRequest(
    var customerId: CustomerIdentifier? = null,
    var deviceId: String = "",
    var uuid: String = "",
    var major: Int = 0,
    var minor: Int = 0,
    var signalPower : Int = 0
)
