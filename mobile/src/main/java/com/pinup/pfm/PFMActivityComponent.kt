package com.pinup.pfm

import com.pinup.pfm.di.scopes.ActivityScope
import com.pinup.pfm.ui.ActivityModule
import com.pinup.pfm.ui.MainActivity
import com.pinup.pfm.ui.UIModule
import com.pinup.pfm.ui.core.view.BaseActivity
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.main_navigator.MainNavigatorFragment
import com.pinup.pfm.ui.main_navigator.adapter.MainNavigatorPagerAdapter
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Component for supported DI components
 */
@ActivityScope
@Subcomponent(modules = arrayOf(ActivityModule::class) )
interface PFMActivityComponent {

//    fun inject(activity: BaseActivity): Unit
    fun inject(activity: MainActivity): Unit

    fun inject(mainNavigatorFragment: MainNavigatorFragment): Unit
    fun inject(mainNavigatorPagerAdapter: MainNavigatorPagerAdapter): Unit
//    fun inject(fragment: BaseFragment): Unit
}