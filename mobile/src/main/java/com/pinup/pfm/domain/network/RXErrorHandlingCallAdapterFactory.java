package com.pinup.pfm.domain.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.pinup.pfm.domain.network.utility.INetworkErrorParser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * Specialized error handler factory for retrofit with rx extension
 */
public class RXErrorHandlingCallAdapterFactory {}
//        extends CallAdapter.Factory {
//
//    private INetworkErrorParser networkErrorHandler;
//    private RxJava2CallAdapterFactory originCallAdaptorFactory;
//
//    public RXErrorHandlingCallAdapterFactory(INetworkErrorParser networkErrorHandler) {
//        this.networkErrorHandler = networkErrorHandler;
//        // All RX call will be executed on a background thread by default
//        originCallAdaptorFactory = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
//    }
//
//    @Override
//    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
//
//        final CallAdapter<?> ca = originCallAdaptorFactory.get(returnType, annotations, retrofit);
//
//        return new CallAdapter<Observable<?>>() {
//            @Override
//            public Type responseType() {
//                return ca.responseType();
//            }
//
//            @Override
//            public <R> Observable<?> adapt(Call<R> call) {
//                final Observable<?> rx = (Observable<?>) ca.adapt(call);
//                return rx.onErrorResumeNext(throwable -> {
//                    return Observable.error(networkErrorHandler.asRetrofitException(throwable));
//                });
//            }
//        };
//    }
//}