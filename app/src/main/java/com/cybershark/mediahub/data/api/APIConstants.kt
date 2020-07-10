package com.cybershark.mediahub.data.api

class APIConstants {
    companion object {
        //API url's
        const val TRAKT_PRODUCTION_API_URL = "https://api.trakt.tv/"
        const val TRAKT_STAGING_API_URL = "https://api-staging.trakt.tv/"
        const val AUTHORIZE_API_URL_PART = "oauth/authorize"
        const val DEEP_LINK_CALLBACK_URI = "cybershark://callback"
        const val AUTH_TOKEN_URL_PART = "oauth/token"

        //API Headers
        const val RESPONSE_TYPE_AUTH = "code"
        const val RESULT_TYPE = "application/json"
        const val GRANT_TYPE = "authorization_code"
        const val TRAKT_API_VERSION = 2

        //API status codes
        const val GET_SUCCESS_CODE = 200
        const val POTS_SUCCESS_CODE = 201
        const val PUT_SUCCESS_CODE = 200
        const val NO_AUTH_REQUEST = 401
        val BAD_REQUEST_CODES by lazy { listOf(403,400,404) }
        const val CONFLICT_REQUEST = 409
        const val RATE_LIMIT_EXCEEDED = 429
        val SERVER_ERROR_CODES by lazy { listOf(500, 503, 504, 520, 521, 522) }
    }
}