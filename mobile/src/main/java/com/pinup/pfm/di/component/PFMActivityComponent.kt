package com.pinup.pfm.di.component

import com.pinup.pfm.di.module.ActivityModule
import com.pinup.pfm.di.scopes.ActivityScope
import com.pinup.pfm.ui.input.action.InputActionContainerActivity
import com.pinup.pfm.ui.main.MainActivity
import dagger.Component

/**
 * Component for supported DI components
 */
@ActivityScope
@Component(dependencies = arrayOf(PFMApplicationComponent::class),
        modules = arrayOf(ActivityModule::class))
interface PFMActivityComponent {

    fun inject(activity: MainActivity): Unit
    fun inject(activity: InputActionContainerActivity)
}