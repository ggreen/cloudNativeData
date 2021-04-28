package com.vmware.data.retail.store.domain

data class Beacon(
    var uuid: String = "",
    var major: Int = 0,
    var minor: Int = 0,
    var category: String = "",
    var entrance: Boolean = false,
    var checkout: Boolean = false
)
{
    var key: String?
    get() = uuid;
    set(value)
    {
        if(value !=null)
            uuid = value
    };
}
