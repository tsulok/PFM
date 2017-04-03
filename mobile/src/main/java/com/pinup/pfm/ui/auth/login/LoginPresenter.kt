package com.pinup.pfm.ui.auth.login

import com.pinup.pfm.domain.network.service.AuthService
import com.pinup.pfm.interactor.auth.IAuthInteractor
import com.pinup.pfm.interactor.user.IUserInteractor
import com.pinup.pfm.ui.core.view.BasePresenter
import com.pinup.pfm.utils.validation.ValidationUtils
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

    fun reauthUserIfPossible() {
        // Simply load main if user is authenticated
        if (authInteractor.isUserAlreadyAuthenticated()) {
            screen?.moveToMain()
        }
    }

    fun loginWithFacebook() {
        screen?.loadingStarted()

        authInteractor.loginFacebook()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    screen?.loadingFinished()
                    screen?.moveToMain()
                }, {
                    screen?.loadingFinished()
                    screen?.loginFailed()
                })
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

    fun registerUser(email: String, password: String) {
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
        userInteractor.signUp(email, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loginWithMail(email, password)
                }, { error ->
                    screen?.loadingFinished()
                    screen?.registrationFailed()
                })
    }

    fun forgotPassword(email: String) {
        val isMailValid = validationUtils.isMailValid(email)

        screen?.hideInputErrors()

        if (!isMailValid) {
            screen?.onMailNotValid()
            screen?.forgotPasswordEmailNotValid()
            return
        }
    }

    fun collectInstalledGoogleAccounts(): List<String> {
        return userInteractor.getAccounts()
    }
}