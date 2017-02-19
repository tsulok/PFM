package com.pinup.pfm.di.component

import com.pinup.pfm.di.scopes.ActivityScope
import com.pinup.pfm.di.module.ActivityModule
import com.pinup.pfm.di.module.FragmentModule
import com.pinup.pfm.ui.MainActivity
import com.pinup.pfm.ui.category.CategoryListFragment
import com.pinup.pfm.ui.category.adapter.CategoryListAdapter
import com.pinup.pfm.ui.charts.ChartListFragment
import com.pinup.pfm.ui.history.HistoryListFragment
import com.pinup.pfm.ui.history.adapter.HistoryListAdapter
import com.pinup.pfm.ui.input.action.InputActionContainerFragment
import com.pinup.pfm.ui.input.action.camera.InputActionCameraFragment
import com.pinup.pfm.ui.input.action.date.InputActionDateFragment
import com.pinup.pfm.ui.input.action.description.InputActionDescriptionFragment
import com.pinup.pfm.ui.input.action.location.InputActionLocationFragment
import com.pinup.pfm.ui.input.container.InputContainerFragment
import com.pinup.pfm.ui.input.keyboard.KeyboardFragment
import com.pinup.pfm.ui.input.main.InputMainFragment
import com.pinup.pfm.ui.input.main.InputMainPresenter
import com.pinup.pfm.ui.main_navigator.MainNavigatorFragment
import com.pinup.pfm.ui.main_navigator.adapter.MainNavigatorPagerAdapter
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Component for supported DI components
 */
@ActivityScope
@Component(dependencies = arrayOf(PFMApplicationComponent::class),
        modules = arrayOf(FragmentModule::class))
interface PFMFragmentComponent {

    fun inject(categoryListFragment: CategoryListFragment)
    fun inject(categoryListFragment: ChartListFragment)
    fun inject(historyListFragment: HistoryListFragment)
    fun inject(inputActionContainerFragment: InputActionContainerFragment)
    fun inject(inputActionCameraFragment: InputActionCameraFragment)
    fun inject(inputActionDateFragment: InputActionDateFragment)
    fun inject(inputActionDescriptionFragment: InputActionDescriptionFragment)
    fun inject(inputActionLocationFragment: InputActionLocationFragment)
}