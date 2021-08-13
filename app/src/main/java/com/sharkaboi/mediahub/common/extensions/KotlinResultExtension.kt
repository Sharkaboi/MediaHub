package com.sharkaboi.mediahub.common.extensions

internal fun <T> Result<T>.getOrNullWithStackTrace(): T? {
    return when {
        isFailure -> {
            exceptionOrNull()?.printStackTrace()
            null
        }
        else -> this.getOrDefault(null)
    }
}
