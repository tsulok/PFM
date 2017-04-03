package com.pinup.pfm.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pinup.pfm.BuildConfig
import com.pinup.pfm.domain.network.RXErrorHandlingCallAdapterFactory
import com.pinup.pfm.domain.network.interceptor.AuthInterceptor
import com.pinup.pfm.domain.network.service.AuthService
import com.pinup.pfm.domain.network.service.CategoryService
import com.pinup.pfm.domain.network.service.UserService
import com.pinup.pfm.domain.network.utility.INetworkErrorParser
import com.pinup.pfm.domain.network.utility.NetworkErrorParser
import com.pinup.pfm.domain.network.utility.PFMDateSerializer
import com.pinup.pfm.interactor.auth.IAuthInteractor
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Module for networking DI
 */
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideDateSerializer(): PFMDateSerializer = PFMDateSerializer()

    @Provides
    @Singleton
    fun provideGson(dateSerializer: PFMDateSerializer): Gson {
        return GsonBuilder()
                .registerTypeAdapter(Date::class.java, dateSerializer)
                .create()
    }

    @Provides
    @Singleton
    fun provideHttpLogger(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return logging
    }

    @Provides
    @Singleton
    fun provideRxErrorHandlingCallAdapterFactory(networkErrorHandler: INetworkErrorParser): RXErrorHandlingCallAdapterFactory {
//        return RXErrorHandlingCallAdapterFactory(networkErrorHandler)
        return RXErrorHandlingCallAdapterFactory()
    }

    @Provides
    @Singleton
    fun provideRetrofit(logger: HttpLoggingInterceptor, auth: AuthInterceptor,
                        gson: Gson,
                        callAdapterFactory: RXErrorHandlingCallAdapterFactory,
                        authInteractor: IAuthInteractor): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(auth)
                .addInterceptor(logger)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(okHttpClient)
//                .addCallAdapterFactory(callAdapterFactory)
                .addCallAdapterFactory(retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        authInteractor.setAuthenticationService(
                retrofit.create(AuthService::class.java))

        return retrofit
    }

    @Provides
    fun provideNetworkErrorHandler(gson: Gson): INetworkErrorParser {
        return NetworkErrorParser(gson)
    }

    // TODO add here api services
    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoryService(retrofit: Retrofit): CategoryService {
        return retrofit.create(CategoryService::class.java)
    }
}