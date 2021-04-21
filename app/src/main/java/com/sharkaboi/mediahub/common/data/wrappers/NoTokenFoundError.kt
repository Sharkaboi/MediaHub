package com.sharkaboi.mediahub.common.data.wrappers

class NoTokenFoundError {
    companion object {
        fun getThrowable(): Throwable {
            return Throwable(message = "Login expired, Log in again")
        }
    }
}