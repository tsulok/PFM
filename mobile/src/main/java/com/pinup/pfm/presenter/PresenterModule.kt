package com.pinup.pfm.presenter

import com.pinup.pfm.ui.history.HistoryPresenter
import com.pinup.pfm.ui.input.action.InputActionContainerPresenter
import com.pinup.pfm.ui.input.action.camera.InputActionCameraPresenter
import com.pinup.pfm.ui.input.action.date.InputActionDatePresenter
import com.pinup.pfm.ui.input.action.description.InputActionDescriptionPresenter
import com.pinup.pfm.ui.input.action.location.InputActionLocationPresenter
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
    fun provideHistoryPresenter(): HistoryPresenter {
        return HistoryPresenter()
    }
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

    @Provides
    @Singleton
    fun provideInputActionContainerPresenter(): InputActionContainerPresenter {
        return InputActionContainerPresenter()
    }

    @Provides
    fun provideInputActionCameraPresenter(): InputActionCameraPresenter {
        return InputActionCameraPresenter()
    }

    @Provides
    fun provideInputActionLocationPresenter(): InputActionLocationPresenter {
        return InputActionLocationPresenter()
    }

    @Provides
    fun provideInputActionDescriptionPresenter(): InputActionDescriptionPresenter {
        return InputActionDescriptionPresenter()
    }

    @Provides
    fun provideInputActionDatePresenter(): InputActionDatePresenter {
        return InputActionDatePresenter()
    }
}