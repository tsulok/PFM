package com.pinup.pfm

import com.pinup.pfm.interactor.category.CategoryInteractor
import com.pinup.pfm.ui.MainActivity
import com.pinup.pfm.ui.UIModule
import dagger.Component
import javax.inject.Singleton

/**
 * Component for supported DI components
 */
@Singleton
@Component(modules = arrayOf(UIModule::class) )
interface PFMApplicationComponent {

    fun inject(activity: MainActivity): Unit

    fun inject(categoryInteractor: CategoryInteractor): Unit
}