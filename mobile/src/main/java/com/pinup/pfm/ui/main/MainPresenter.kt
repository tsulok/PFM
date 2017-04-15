package com.pinup.pfm.ui.main

import com.pinup.pfm.domain.manager.content.IContentManager
import com.pinup.pfm.domain.manager.sync.ISyncManager
import com.pinup.pfm.interactor.category.ICategoryInteractor
import com.pinup.pfm.interactor.utils.ICurrencyInteractor
import com.pinup.pfm.common.ui.core.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*
import javax.inject.Inject

/**
 * Presenter for main
 */

class MainPresenter
@Inject constructor(private val categoryInteractor: ICategoryInteractor,
                    private val syncManager: ISyncManager,
                    private val currencyInteractor: ICurrencyInteractor,
                    private val contentManager: IContentManager) : BasePresenter<MainScreen>() {

    fun initMain() {
        screen?.loadingStarted()

        contentManager.downloadContents()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { handleDownloadContentSuccess() },
                        { handleDownloadContentFailed(it) })

        syncManager.uploadUnsyncedTransactions()

    }

    private fun handleDownloadContentSuccess() {
        screen?.loadingFinished()

        if (currencyInteractor.getSelectedCurrency() != null) {
            screen?.loadMainNavigation()
        } else {
            screen?.showDefaultCurrencyChooser(currencyInteractor.listAvailableCurrencies())
        }
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

    fun updateSelectedCurrency(currency: Currency) {
        currencyInteractor.updateSelectedCurrency(currency)
        screen?.loadMainNavigation()
    }
}