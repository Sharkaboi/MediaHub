package com.sharkaboi.mediahub.common.extensions

import androidx.paging.PagingSource
import com.sharkaboi.mediahub.data.wrappers.MHError
import com.sharkaboi.mediahub.data.wrappers.MHTaskState
import kotlinx.coroutines.*
import timber.log.Timber

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

suspend fun <T> getCatching(block: suspend () -> MHTaskState<T>): MHTaskState<T> {
    return withContext(Dispatchers.IO) {
        try {
            block()
        } catch (e: Exception) {
            e.printStackTrace()
            Timber.d(e.message ?: String.emptyString)
            return@withContext MHTaskState(
                isSuccess = false,
                data = null,
                error = MHError.getError(e.message, MHError.UnknownError)
            )
        }
    }
}

suspend fun <T1 : Any, T2 : Any> getCatchingPaging(
    block: suspend () -> PagingSource.LoadResult<T1, T2>
): PagingSource.LoadResult<T1, T2> {
    return withContext(Dispatchers.IO) {
        try {
            block()
        } catch (e: Exception) {
            e.printStackTrace()
            Timber.d(e.message ?: String.emptyString)
            return@withContext PagingSource.LoadResult.Error(e)
        }
    }
}