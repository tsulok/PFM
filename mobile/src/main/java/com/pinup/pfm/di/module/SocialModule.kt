package com.pinup.pfm.di.module

import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.pinup.pfm.di.qualifiers.Facebook
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Module for social
 */
@Module
class SocialModule {

    @Provides
    @Facebook
    @Singleton
    fun provideCallbackManager(): CallbackManager {
        return CallbackManager.Factory.create()
    }

    @Provides
    @Facebook
    @Singleton
    fun provideLoginManager(): LoginManager {
        return LoginManager.getInstance()
    }
}