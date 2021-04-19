package com.sharkaboi.mediahub.common.data.wrappers

data class MHTaskState<T>(
    val isSuccess: Boolean,
    val data: T?,
    val error: MHError
)