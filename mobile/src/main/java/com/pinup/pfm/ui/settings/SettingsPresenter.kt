package com.pinup.pfm.ui.settings

import com.pinup.pfm.domain.manager.content.IContentManager
import com.pinup.pfm.domain.repository.manager.IRepositoryManager
import com.pinup.pfm.interactor.auth.IAuthInteractor
import com.pinup.pfm.ui.core.view.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*
import javax.inject.Inject

/**
 * Presenter for Settings
 */
class SettingsPresenter
@Inject constructor(private val authInteractor: IAuthInteractor,
                    private val contentManager: IContentManager,
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
                    screen?.loadingFinished()
                    screen?.updateLastSyncTime(contentManager.getLastSyncTime())
                }, {
                    screen?.loadingFinished()
                    screen?.updateFailed()
                })
    }

    /**
     * View should initialized
     */
    fun initView() {
        val mail = authInteractor.getCurrentMailAddress() ?: ""
        val syncTs = contentManager.getLastSyncTime()
        screen?.initView(SettingsViewModel(lastSyncTime = syncTs, userEmail = mail))
    }
}

private class SettingsViewModel
constructor(override val lastSyncTime: Date?,
            override val userEmail: String) : ISettingsViewModel {
}