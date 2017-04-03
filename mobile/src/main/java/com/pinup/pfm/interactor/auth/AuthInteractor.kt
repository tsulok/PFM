package com.pinup.pfm.interactor.auth

import com.pinup.pfm.domain.manager.auth.IAuthenticationManager
import com.pinup.pfm.domain.network.dto.auth.LoginMailAuthNetworkModel
import com.pinup.pfm.domain.network.dto.auth.LoginResponseModel
import com.pinup.pfm.domain.network.service.AuthService
import com.pinup.pfm.interactor.social.facebook.IFacebookInteractor
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Interactor for authentication
 */

@Singleton
class AuthInteractor
@Inject constructor(val authenticationManager: IAuthenticationManager,
                    val facebookInteractor: IFacebookInteractor) : IAuthInteractor {

    object Constants {
        @JvmStatic val PREF_KEY_MAIL = "pfm.user.email"
        @JvmStatic val PREF_KEY_PASS = "pfm.user.password"
    }

    lateinit var authService: AuthService

    override fun setAuthenticationService(authenticationService: AuthService) {
        this.authService = authenticationService
    }

    override fun reauthenticateUser(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun login(email: String, password: String): Completable {
        val request = LoginMailAuthNetworkModel(email, password)
        return Completable.fromObservable<LoginResponseModel> {
            authService.login(request)
                    .doOnNext { response ->
                        authenticationManager.storeToken(response.accessToken)
                    }
        }
    }

    override fun loginFacebook(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isUserAlreadyAuthenticated(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun signUp(email: String, fullName: String, password: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun forgotPassword(email: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun logout(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearCredentials() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

interface IAuthInteractor {

    /**
     * Set the authentication service for this component
     * Should do manually because of a dependency circle
     * @param authenticationService The retrofit authentication service
     */
    fun setAuthenticationService(authenticationService: AuthService)

    /**
     * Reauthenticates the user
     * @return An completable action on the result
     */
    fun reauthenticateUser(): Completable

    /**
     * Executes the login action
     * @param email The email of the user
     * *
     * @param password Password of the user
     * *
     * @return A completable action on success
     */
    fun login(email: String, password: String): Completable

    /**
     * Executes the login action with facebook
     * @return A completable action on success
     */
    fun loginFacebook(): Completable

    /**
     * Checks whether the user already entered it's credentials
     * @return True if the user authorised false otherwise
     */
    fun isUserAlreadyAuthenticated(): Boolean

    /**
     * This will signup a user and will login too.
     */
    fun signUp(email: String, fullName: String, password: String): Completable

    /**
     * This will send a notification to the user's email.
     */
    fun forgotPassword(email: String): Completable

    /**
     * This will log out the user from the server.
     */
    fun logout(): Completable

    /**
     * All credentials should be cleared
     */
    fun clearCredentials()
}