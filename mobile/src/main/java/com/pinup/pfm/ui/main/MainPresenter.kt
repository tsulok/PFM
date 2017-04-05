package com.pinup.pfm.ui.main

import com.pinup.pfm.domain.manager.content.IContentManager
import com.pinup.pfm.interactor.category.ICategoryInteractor
import com.pinup.pfm.ui.core.view.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Presenter for main
 */

class MainPresenter
@Inject constructor(private val categoryInteractor: ICategoryInteractor,
                    private val contentManager: IContentManager) : BasePresenter<MainScreen>() {

    fun initMain() {
        screen?.loadingStarted()

        contentManager.downloadContents()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { handleDownloadContentSuccess() },
                        { handleDownloadContentFailed(it) })
    }

    private fun handleDownloadContentSuccess() {
        screen?.loadingFinished()
        screen?.loadMainNavigation()
    }

    private fun handleDownloadContentFailed(e: Throwable) {
        screen?.loadingFinished()
        if (isInitialContentLoaded()) {
            screen?.syncFailedButHasInitialData()
            screen?.loadMainNavigation()
        } else {
            screen?.loadInitialDataFailedToLoad()
        }
    }

    private fun isInitialContentLoaded() = categoryInteractor.listAllSelectableCategories().isNotEmpty()
}