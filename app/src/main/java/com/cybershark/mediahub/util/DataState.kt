package com.cybershark.mediahub.util

sealed class DataState<out R> {
    data class COMPLETED<out T>(val data: T) : DataState<T>()
    data class ERROR(val exception: Exception) : DataState<Nothing>()
    object LOADING : DataState<Nothing>()
}
// TODO: 8/25/2020 Add more complex logic with kotlin flows and mvi principles of data flow.