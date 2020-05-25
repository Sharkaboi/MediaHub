package com.cybershark.mediahub.util

import java.net.InetAddress

class InternetConnectionManager {
    companion object {
        fun isInternetActive(): Boolean {
            return try {
                val ping = InetAddress.getByName("google.com")
                ping.isReachable(1000)
            } catch (e: Exception) {
                false
            }
        }
    }
}