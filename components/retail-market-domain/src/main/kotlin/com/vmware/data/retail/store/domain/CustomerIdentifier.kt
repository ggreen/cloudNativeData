package com.vmware.data.retail.store.domain

data class CustomerIdentifier(
    var key: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var mobileNumber: String = ""
)