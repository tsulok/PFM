package com.pinup.pfm.domain.network.utility

import com.pinup.pfm.domain.network.dto.NetworkError
import com.pinup.pfm.domain.network.exception.RetrofitException


/**
 * Handle the common error messages from the network
 */
class NetworkErrorHandler {

    fun handleNetworkError(throwable: Throwable, errorListener: INetworkErrorListener) {
        if (throwable is RetrofitException) {
            val retrofitException = throwable

            when (throwable.kind) {
                RetrofitException.Kind.NETWORK -> errorListener.onNetworkErrorOccurred()
                RetrofitException.Kind.HTTP -> if (retrofitException.networkError != null) {
                    errorListener.onHttpErrorOccurred(retrofitException.networkError)
                } else {
                    errorListener.onHttpErrorOccurred()
                }
                else -> errorListener.onErrorNotSupported()
            }
        } else {
            errorListener.onNotNetworkError(throwable)
        }
    }
}

interface INetworkErrorListener {
    fun onNetworkErrorOccurred()

    fun onHttpErrorOccurred(networkError: NetworkError?)

    fun onHttpErrorOccurred()

    fun onHttpErrorOccurred(message: String)

    fun onErrorNotSupported()

    fun onNotNetworkError(throwable: Throwable)
}