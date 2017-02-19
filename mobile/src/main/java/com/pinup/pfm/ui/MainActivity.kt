package com.pinup.pfm.ui

import android.os.Bundle
import com.pinup.pfm.R
import com.pinup.pfm.di.component.PFMActivityComponent
import com.pinup.pfm.interactor.category.CategoryInteractor
import com.pinup.pfm.ui.core.view.BaseActivity
import com.pinup.pfm.ui.main_navigator.MainNavigatorFragment
import javax.inject.Inject

class MainActivity : BaseActivity() {

    val mainNavigatorFragment: MainNavigatorFragment by lazy { MainNavigatorFragment() }

    @Inject lateinit var categoryInteractor: CategoryInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoryInteractor.createTestData()

        setContentView(R.layout.activity_main)
//        val toolbar = find<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)

        switchToFragment(mainNavigatorFragment)
//        switchToFragment(TestFragment())
    }

    override fun injectActivity(component: PFMActivityComponent) {
        component.inject(this)
    }

    override fun getActivityMainContainer(): Int {
        return R.id.containerMain
    }
}
