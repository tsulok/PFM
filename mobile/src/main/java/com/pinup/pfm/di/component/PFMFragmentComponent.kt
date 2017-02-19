package com.pinup.pfm.di.component

import com.pinup.pfm.di.module.FragmentModule
import com.pinup.pfm.di.scopes.FragmentScope
import com.pinup.pfm.ui.category.CategoryListFragment
import com.pinup.pfm.ui.charts.ChartListFragment
import com.pinup.pfm.ui.history.HistoryListFragment
import com.pinup.pfm.ui.input.action.InputActionContainerFragment
import com.pinup.pfm.ui.input.action.camera.InputActionCameraFragment
import com.pinup.pfm.ui.input.action.date.InputActionDateFragment
import com.pinup.pfm.ui.input.action.description.InputActionDescriptionFragment
import com.pinup.pfm.ui.input.action.location.InputActionLocationFragment
import com.pinup.pfm.ui.input.container.InputContainerFragment
import com.pinup.pfm.ui.input.keyboard.KeyboardFragment
import com.pinup.pfm.ui.input.main.InputMainFragment
import com.pinup.pfm.ui.main_navigator.MainNavigatorFragment
import com.pinup.pfm.ui.settings.SettingsFragment
import dagger.Component

/**
 * Component for supported DI components
 */
@FragmentScope
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
    fun inject(inputContainerFragment: InputContainerFragment)
    fun inject(keyboardFragment: KeyboardFragment)
    fun inject(inputMainFragment: InputMainFragment)
    fun inject(mainNavigatorFragment: MainNavigatorFragment)
    fun inject(settingsFragment: SettingsFragment)
}