package com.sharkaboi.mediahub.data.wrappers

import com.sharkaboi.mediahub.common.extensions.emptyString

data class MHError(
    val errorMessage: String,
    val throwable: Throwable?
) {
    companion object {
        val EmptyError = MHError(String.emptyString, null)
    }
}
