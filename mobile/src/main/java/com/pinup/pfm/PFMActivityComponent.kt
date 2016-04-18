package com.pinup.pfm

import com.pinup.pfm.di.scopes.ActivityScope
import com.pinup.pfm.ui.ActivityModule
import com.pinup.pfm.ui.MainActivity
import com.pinup.pfm.ui.category.CategoryListFragment
import com.pinup.pfm.ui.category.adapter.CategoryListAdapter
import com.pinup.pfm.ui.history.HistoryListFragment
import com.pinup.pfm.ui.history.adapter.HistoryListAdapter
import com.pinup.pfm.ui.input.container.InputContainerFragment
import com.pinup.pfm.ui.input.keyboard.KeyboardFragment
import com.pinup.pfm.ui.input.main.InputMainFragment
import com.pinup.pfm.ui.input.main.InputMainPresenter
import com.pinup.pfm.ui.main_navigator.MainNavigatorFragment
import com.pinup.pfm.ui.main_navigator.adapter.MainNavigatorPagerAdapter
import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Component for supported DI components
 */
@Singleton
@ActivityScope
@Subcomponent(modules = arrayOf(ActivityModule::class) )
interface PFMActivityComponent {

    fun inject(activity: MainActivity): Unit

    fun inject(inputContainerFragment: InputContainerFragment): Unit
    fun inject(inputKeyboardFragment: KeyboardFragment): Unit
    fun inject(inputMainFragment: InputMainFragment): Unit
    fun inject(inputMainPresenter: InputMainPresenter): Unit

    fun inject(categoryListFragment: CategoryListFragment): Unit
    fun inject(categoryListAdapter: CategoryListAdapter): Unit

    fun inject(mainNavigatorFragment: MainNavigatorFragment): Unit
    fun inject(mainNavigatorPagerAdapter: MainNavigatorPagerAdapter): Unit

    fun inject(historyListFragment: HistoryListFragment): Unit
    fun inject(historyListAdapter: HistoryListAdapter): Unit
}