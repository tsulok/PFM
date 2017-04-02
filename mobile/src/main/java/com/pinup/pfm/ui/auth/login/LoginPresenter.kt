package com.pinup.pfm.ui.auth.login

import com.pinup.pfm.interactor.user.IUserInteractor
import com.pinup.pfm.ui.core.view.BasePresenter
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Presenter for login
 */
class LoginPresenter
@Inject constructor(val userInteractor: IUserInteractor): BasePresenter<LoginScreen>() {

    fun loginWithFacebook() {
        screen?.loadingStarted()

        val completable = Completable.create { e ->
            Thread.sleep(2000)
            e.onComplete()
        }

        completable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    screen?.loadingFinished()
                    screen?.moveToMain()
                })

        // TODO facebook login
    }

    fun collectInstalledGoogleAccounts(): List<String> {
        return userInteractor.getAccounts()
    }
}