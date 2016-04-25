package com.pinup.pfm.ui

import android.content.res.Resources
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.orhanobut.logger.Logger
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.interactor.category.CategoryInteractor
import com.pinup.pfm.test.TestFragment
import com.pinup.pfm.ui.core.view.BaseActivity
import com.pinup.pfm.ui.main_navigator.MainNavigatorFragment
import org.jetbrains.anko.find
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var mainNavigatorFragment: MainNavigatorFragment
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

    override fun injectActivityComponent() {
        PFMApplication.activityInjector?.inject(this)
    }

    override fun getActivityMainContainer(): Int {
        return R.id.containerMain
    }
}
