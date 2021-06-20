package com.sharkaboi.mediahub.common.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> debounce(
    delay: Long = 800L,
    scope: CoroutineScope,
    callback: (T?) -> Unit
): (T?) -> Unit {
    var debounceJob: Job? = null
    return { param: T? ->
        debounceJob?.cancel()
        debounceJob = scope.launch {
            delay(delay)
            callback(param)
        }
    }
}
