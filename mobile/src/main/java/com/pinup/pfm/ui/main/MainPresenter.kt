package com.pinup.pfm.ui.main

import com.pinup.pfm.interactor.category.ICategoryInteractor
import com.pinup.pfm.ui.core.view.BasePresenter
import javax.inject.Inject

/**
 * Presenter for main
 */

class MainPresenter @Inject constructor(private val categoryInteractor: ICategoryInteractor) : BasePresenter<MainScreen>() {

    fun initMain() {
        categoryInteractor.createTestData()

        screen?.loadMainNavigation()
    }
}