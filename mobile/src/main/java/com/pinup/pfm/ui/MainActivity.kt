package com.pinup.pfm.ui

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.interactor.category.CategoryInteractor
import com.pinup.pfm.ui.core.view.BaseActivity
import org.jetbrains.anko.find
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var categoryInteractor: CategoryInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PFMApplication.injector.inject(this)

        setContentView(R.layout.activity_main)
        val toolbar = find<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun getActivityMainContainer(): Int {
        return R.id.containerMain
    }
}
