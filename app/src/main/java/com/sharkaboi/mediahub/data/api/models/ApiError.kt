package com.sharkaboi.mediahub.data.api.models

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class ApiError(
    val error: String,
    val message: String
) {
    fun getThrowable(): Throwable {
        return Throwable(message = message)
    }

    companion object {
        val DefaultError =
            ApiError(message = "An error occurred with MAL server", error = "unknown_error")
    }
}
