package com.pinup.pfm.ui.main

import android.os.Bundle
import com.pinup.pfm.R
import com.pinup.pfm.di.component.PFMActivityComponent
import com.pinup.pfm.ui.core.view.BaseActivity
import com.pinup.pfm.ui.core.view.BaseScreen
import com.pinup.pfm.ui.core.view.IBasePresenter
import com.pinup.pfm.ui.input.action.InputActionContainerPresenter
import com.pinup.pfm.ui.main_navigator.MainNavigatorFragment
import javax.inject.Inject

class MainActivity : BaseActivity(), MainScreen {

    @Inject lateinit var mainPresenter: MainPresenter

    val mainNavigatorFragment: MainNavigatorFragment by lazy { MainNavigatorFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun loadContentId(): Int = R.layout.activity_main

    override fun initObjects() {
        mainPresenter.initMain()
    }

    override fun injectActivity(component: PFMActivityComponent) {
        component.inject(this)
    }

    override fun getActivityMainContainer(): Int {
        return R.id.containerMain
    }

    override fun loadMainNavigation() {
        switchToFragment(mainNavigatorFragment)
    }

    override fun getPresenter(): IBasePresenter? = mainPresenter

    override fun getScreen(): BaseScreen = this
}