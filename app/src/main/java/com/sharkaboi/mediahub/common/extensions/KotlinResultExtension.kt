package com.sharkaboi.mediahub.common.extensions

internal fun <T> Result<T>.getOrNullWithStackTrace(): T? {
    return when {
        isFailure -> null
        else -> this.getOrDefault(null)
    }
}