package com.vmware.data.retail.store.domain

data class ProductAssociate(
    var post: Array<String?>?,
    var pre: String? = null) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductAssociate

        if (post != null) {
            if (other.post == null) return false
            if (!post.contentEquals(other.post)) return false
        } else if (other.post != null) return false
        if (pre != other.pre) return false

        return true
    }

    override fun hashCode(): Int {
        var result = post?.contentHashCode() ?: 0
        result = 31 * result + (pre?.hashCode() ?: 0)
        return result
    }

}
