package com.pinup.pfm.di.module

import android.content.Context
import android.support.v4.app.FragmentManager
import com.pinup.pfm.di.qualifiers.ActivityContext
import com.pinup.pfm.di.qualifiers.ChildFragmentManager
import com.pinup.pfm.ui.core.view.BaseFragment
import dagger.Module
import dagger.Provides

/**
 * DI modules for Activity
 */
@Module
class FragmentModule(val fragment: BaseFragment) {

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return fragment.context
    }

    @ChildFragmentManager
    @Provides
    fun provideFragmentManager(): FragmentManager {
        return fragment.childFragmentManager
    }
}