package com.pinup.pfm.ui.auth.login

import com.pinup.pfm.domain.network.service.AuthService
import com.pinup.pfm.interactor.auth.IAuthInteractor
import com.pinup.pfm.interactor.user.IUserInteractor
import com.pinup.pfm.ui.core.view.BasePresenter
import com.pinup.pfm.utils.validation.ValidationUtils
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Presenter for login
 */
class LoginPresenter
@Inject constructor(val userInteractor: IUserInteractor,
                    val authService: AuthService,
                    val authInteractor: IAuthInteractor,
                    val validationUtils: ValidationUtils): BasePresenter<LoginScreen>() {

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

    fun loginWithMail(email: String, password: String) {
        val isMailValid = validationUtils.isMailValid(email)
        val isPasswordValid = validationUtils.isPasswordValid(password)

        screen?.hideInputErrors()

        if (!isMailValid || !isPasswordValid) {

            if (!isMailValid) {
                screen?.onMailNotValid()
            }

            if (!isPasswordValid) {
                screen?.onPasswordNotValid()
            }

            return
        }

        screen?.loadingStarted()

        authInteractor.login(email, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    screen?.loadingFinished()
                    screen?.moveToMain()
                }, { error ->
                    screen?.loadingFinished()
                    screen?.loginFailed()
                })
    }

    fun collectInstalledGoogleAccounts(): List<String> {
        return userInteractor.getAccounts()
    }
}