package com.pinup.pfm.domain.network.exception

import com.pinup.pfm.domain.network.dto.NetworkError
import java.io.IOException

/**
 * Exception wrapper for retrofit
 */
class RetrofitException constructor(override val message: String? = null,
                                    val kind: Kind,
                                    val networkError: NetworkError? = null,
                                    val exception: Throwable? = null): RuntimeException() {

    companion object {}

    /**
     * Identifies the event kind which triggered a {@link RetrofitException}.
     */
    enum class Kind {
        /**
         * An {@link IOException} occurred while communicating to the server.
         */
        NETWORK,
        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED,
        /**
         * A parsing exception when the reported answer couldn't be parsed properly
         */
        PARSE
    }
}

fun RetrofitException.Companion.networkError(exception: IOException): RetrofitException {
    return RetrofitException(exception.message, RetrofitException.Kind.NETWORK, null, exception)
}

fun RetrofitException.Companion.unexpectedError(exception: Throwable): RetrofitException {
    return RetrofitException(exception.message, RetrofitException.Kind.UNEXPECTED, null, exception)
}

fun RetrofitException.Companion.parseError(exception: Throwable): RetrofitException {
    return RetrofitException(exception.message, RetrofitException.Kind.PARSE, null, exception)
}

fun RetrofitException.Companion.httpError(url: String, networkError: NetworkError?,
                                          responseCode: Int, responseMessage: String): RetrofitException {
    val message = "{$responseCode} - {$responseMessage}"
    return RetrofitException(message, RetrofitException.Kind.HTTP, networkError)
}