package com.sharkaboi.mediahub.data.wrappers

import com.sharkaboi.mediahub.common.extensions.emptyString

data class MHError(
    val errorMessage: String
) {
    fun getThrowable() = Throwable(message = errorMessage)

    companion object {
        val EmptyError = MHError(String.emptyString)
        val UnknownError = MHError("An unknown error occurred")
        val InvalidStateError = MHError("An invalid state was reached")
        val LoginExpiredError = MHError("Login expired, Log in again")
        val NetworkError = MHError("Error with network")
        val ParsingError = MHError("Error with parsing")
        val ProtocolError = MHError("Protocol error")
        val ApplicationError = MHError("Application error")
        val AnimeNotFoundError = MHError("Anime isn't in your list")
        val MangaNotFoundError = MHError("Manga isn't in your list")
        fun apiErrorWithCode(code: Int) = MHError("Error with status code : $code")

        fun getError(message: String?, error: MHError = UnknownError) =
            message?.let { MHError(it) } ?: error
    }
}
