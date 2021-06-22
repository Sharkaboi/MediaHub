package com.sharkaboi.mediahub.data.wrappers

class NoTokenFoundError {
    companion object {
        fun getThrowable(): Throwable {
            return Throwable(message = "Login expired, Log in again")
        }
    }
}