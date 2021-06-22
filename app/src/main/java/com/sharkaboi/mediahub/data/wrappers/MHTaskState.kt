package com.sharkaboi.mediahub.data.wrappers

data class MHTaskState<T>(
    val isSuccess: Boolean,
    val data: T?,
    val error: MHError
)