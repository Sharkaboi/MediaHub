package com.sharkaboi.mediahub.data.api.models

import androidx.annotation.Keep
import com.sharkaboi.mediahub.common.extensions.ifNullOrBlank
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class ApiError(
    val error: String,
    val message: String
) {
    fun getThrowable(): Throwable {
        return Throwable(message = message.ifNullOrBlank { defaultErrorMessage })
    }

    companion object {
        private const val defaultErrorMessage = "An error occurred with MAL server"
        val DefaultError =
            ApiError(message = defaultErrorMessage, error = "unknown_error")
    }
}
