package com.cybershark.mediahub.util

sealed class STATUS {
    object IDLE : STATUS()
    object LOADING : STATUS()
    data class ERROR(val message: String) : STATUS()
    data class COMPLETED(val message: String) : STATUS()
}