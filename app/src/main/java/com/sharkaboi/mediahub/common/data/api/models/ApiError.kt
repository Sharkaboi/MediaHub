package com.sharkaboi.mediahub.common.data.api.models

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
