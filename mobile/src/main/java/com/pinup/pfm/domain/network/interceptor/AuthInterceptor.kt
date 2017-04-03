package com.pinup.pfm.domain.network.interceptor

import com.pinup.pfm.domain.manager.auth.IAuthenticationManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Interceptor for authenticated requests
 */
class AuthInterceptor
@Inject constructor(val authenticationManager: IAuthenticationManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        val currentToken = authenticationManager.token

        if (chain == null) {
            return Response.Builder().build()
        }

        if (currentToken != null) {
            val originRequest = chain.request()
            val processed = originRequest.newBuilder()
                    .addHeader("Authorization", String.format("Bearer %s", currentToken))
                    .build()

            return chain.proceed(processed)
        }

        return chain.proceed(chain.request())
    }
}