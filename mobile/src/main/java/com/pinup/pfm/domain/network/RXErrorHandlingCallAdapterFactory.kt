package com.pinup.pfm.domain.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.pinup.pfm.R
import com.pinup.pfm.domain.network.utility.INetworkErrorParser
import com.pinup.pfm.domain.network.utility.NetworkErrorHandler
import com.pinup.pfm.domain.network.utility.NetworkErrorParser
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Specialized error handler factory for retrofit with rx extension
 */
class RXErrorHandlingCallAdapterFactory
constructor(val networkErrorParser: INetworkErrorParser): CallAdapter.Factory() {

    private val originCallAdapterFactory: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

    override fun get(returnType: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): CallAdapter<*> {
        val ca = originCallAdapterFactory.get(returnType, annotations, retrofit)

        return object : CallAdapter<Observable<*>> {
            override fun responseType(): Type {
                return ca.responseType()
            }

            override fun <R : Any?> adapt(call: Call<R>?): Observable<*> {
                val rx = ca.adapt(call) as Observable<*>
                rx.onErrorResumeNext {  }
            }
        }
    }
}