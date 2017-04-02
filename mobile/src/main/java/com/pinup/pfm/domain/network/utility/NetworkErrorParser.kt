package com.pinup.pfm.domain.network.utility

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.pinup.pfm.domain.network.dto.NetworkError
import com.pinup.pfm.domain.network.exception.*
import java.io.IOException
import javax.inject.Inject


/**
 * Network error handler
 */
class NetworkErrorParser
@Inject constructor(val gson: Gson): INetworkErrorParser {

    override fun asRetrofitException(throwable: Throwable): RetrofitException {
        // We had non-200 http error
        if (throwable is HttpException) {
            val response = throwable.response()
            var networkError: NetworkError? = null
            try {
                if (response.errorBody() != null) {
                    try {
                        networkError = gson.fromJson(response.errorBody().string(), NetworkError::class.java)
                    } catch (e: JsonSyntaxException) {
                        return RetrofitException.parseError(throwable)
                    }

                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return RetrofitException.httpError(response.raw().request().url().toString(), networkError,
                    response.code(), response.message())
        }

        // A network error happened
        if (throwable is IOException) {
            return RetrofitException.networkError(throwable)
        }

        // We don't know what happened. We need to simply convert to an unknown error

        return RetrofitException.unexpectedError(throwable)
    }
}

interface INetworkErrorParser {
    fun asRetrofitException(throwable: Throwable): RetrofitException
}