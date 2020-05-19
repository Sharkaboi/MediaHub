package com.cybershark.mediahub.data.api

class ApiConstants {
    companion object {
        const val TRAKT_PRODUCTION_API_URL = "https://api.trakt.tv"
        const val TRAKT_STAGING_API_URL = "https://api-staging.trakt.tv"
        const val TRAKT_API_VERSION = 2
        const val GET_SUCCESS_CODE = 200
        const val POTS_SUCCESS_CODE = 201
        const val PUT_SUCCESS_CODE = 200
        const val BAD_REQUEST_CODE = 400
        const val NO_AUTH_REQUEST = 401
        const val BAD_REQUEST = 403
        const val NOT_FOUND = 404
        const val CONFLICT_REQUEST = 409
        const val RATE_LIMIT_EXCEEDED = 429
        val SERVER_ERROR_CODES = listOf(500, 503, 504, 520, 521, 522)
        const val RESULT_TYPE = "application/json"
    }
}