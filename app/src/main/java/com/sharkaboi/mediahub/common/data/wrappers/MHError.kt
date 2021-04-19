package com.sharkaboi.mediahub.common.data.wrappers

import com.sharkaboi.mediahub.common.extensions.emptyString

data class MHError(
    val errorMessage: String,
    val throwable: Throwable?
) {
    companion object {
        val nullError = MHError(String.emptyString, null)
    }
}