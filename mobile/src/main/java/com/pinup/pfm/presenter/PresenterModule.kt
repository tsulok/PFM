package com.pinup.pfm.presenter

import com.pinup.pfm.ui.input.container.InputContainerPresenter
import com.pinup.pfm.ui.input.main.InputMainPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * DI modules for Presenters
 */
@Module
class PresenterModule {

    @Provides
    @Singleton
    fun provideInputMainPresenter(): InputMainPresenter {
        return InputMainPresenter()
    }

    @Provides
    @Singleton
    fun provideInputContainerPresenter(): InputContainerPresenter {
        return InputContainerPresenter()
    }
}