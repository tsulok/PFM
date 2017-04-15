package com.pinup.pfm.ui.settings

import com.pinup.pfm.domain.event.CurrencyChangedEvent
import com.pinup.pfm.domain.event.TransactionUpdatedEvent
import com.pinup.pfm.domain.manager.content.IContentManager
import com.pinup.pfm.domain.manager.preferences.SharedPreferencesManager
import com.pinup.pfm.domain.manager.sync.ISyncManager
import com.pinup.pfm.domain.repository.manager.IRepositoryManager
import com.pinup.pfm.interactor.auth.IAuthInteractor
import com.pinup.pfm.interactor.utils.ICurrencyInteractor
import com.pinup.pfm.common.ui.core.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import org.greenrobot.eventbus.EventBus
import java.util.*
import javax.inject.Inject

/**
 * Presenter for Settings
 */
class SettingsPresenter
@Inject constructor(private val authInteractor: IAuthInteractor,
                    private val contentManager: IContentManager,
                    private val eventBus: EventBus,
                    private val syncManager: ISyncManager,
                    private val preferencesManager: SharedPreferencesManager,
                    private val currencyInteractor: ICurrencyInteractor,
                    private val repositoryManager: IRepositoryManager)
    : BasePresenter<SettingsScreen>() {

    fun onMainNavigationButtonClicked() {
        screen?.navigateToMain()
    }

    fun onLogoutClicked(isLogoutConfirmed: Boolean) {
        if (!isLogoutConfirmed) {
            screen?.askUserConfirmLogout()
            return
        }

        authInteractor.clearCredentials()
        preferencesManager.clearAllPreference()
        repositoryManager.clearDatabase()
        screen?.logoutUser()
    }

    /**
     * Settings button is clicked -> sync process needed
     */
    fun onSyncButtonClicked() {
        screen?.loadingStarted()

        contentManager.downloadContents()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    eventBus.post(TransactionUpdatedEvent())
                    screen?.loadingFinished()
                    screen?.updateLastSyncTime(contentManager.getLastSyncTime())
                }, { e ->
                    screen?.loadingFinished()
                    screen?.updateFailed()
                })
        syncManager.uploadUnsyncedTransactions()
    }

    fun listSelectedCurrencies() {
        val currencies = currencyInteractor.listAvailableCurrencies()
        val defaultCurrency = currencyInteractor.getSelectedCurrency()
        val selectedIndex = currencies.indexOf(defaultCurrency)

        screen?.showCurrencySelector(currencies, selectedIndex)
    }

    /**
     * View should initialized
     */
    fun initView() {
        val mail = authInteractor.getCurrentMailAddress() ?: ""
        val syncTs = contentManager.getLastSyncTime()
        screen?.initView(SettingsViewModel(lastSyncTime = syncTs, userEmail = mail))
        screen?.updateSelectedCurrency(currencyInteractor.getSelectedCurrency())
    }

    fun updateSelectedCurrency(currency: Currency) {
        currencyInteractor.updateSelectedCurrency(currency)
        screen?.updateSelectedCurrency(currency)
        eventBus.post(CurrencyChangedEvent(currency))
    }
}

private class SettingsViewModel
constructor(override val lastSyncTime: Date?,
            override val userEmail: String) : ISettingsViewModel {
}