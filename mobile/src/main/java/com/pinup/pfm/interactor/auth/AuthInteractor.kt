package com.pinup.pfm.interactor.auth

import com.pinup.pfm.domain.manager.auth.IAuthenticationManager
import com.pinup.pfm.domain.manager.preferences.SharedPreferencesManager
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
                    val facebookInteractor: IFacebookInteractor,
                    val preferencesHelper: SharedPreferencesManager) : IAuthInteractor {

    object Constants {
        @JvmStatic val PREF_KEY_MAIL = "pfm.user.email"
        @JvmStatic val PREF_KEY_PASS = "pfm.user.password"
    }

    lateinit var authService: AuthService

    override fun setAuthenticationService(authenticationService: AuthService) {
        this.authService = authenticationService
    }

    override fun reauthenticateUser(): Completable {
        // If facebook data is available - reauth as a facebook user
        if (facebookInteractor.storedFacebookData != null) {
            return loginFacebook()
        }

        val credentials = getSimpleCredentials() ?: return Completable.error(LoginFailedException("Credentials wasn't found but tried to reauthenticate"))

        // Otherwise reauth as a simple user
        return login(credentials.first, credentials.second)
    }

    override fun login(email: String, password: String): Completable {
        return Completable.fromObservable(
                authService.login(email, password)
                        .doOnNext { response ->
                            authenticationManager.storeToken(response.accessToken)
                        }.doOnNext { saveSimpleCredentials(email, password) })
    }

    override fun loginFacebook(): Completable {
        val storedFacebookData = facebookInteractor.storedFacebookData ?: return Completable.error(LoginFailedException())

        return Completable.fromObservable(
                authService.loginSocial(storedFacebookData.token)
                        .doOnNext { response ->
                            authenticationManager.storeToken(response.accessToken)
                        })
    }

    override fun isUserAlreadyAuthenticated(): Boolean {
        return facebookInteractor.storedFacebookData != null ||
                getSimpleCredentials() != null
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

    /**
     * Returns the simple credentials if any

     * @return The simple credentials for the logged user
     */
    private fun getSimpleCredentials(): Pair<String, String>? {
        val email = preferencesHelper.getStringPreference(Constants.PREF_KEY_MAIL)
        val password = preferencesHelper.getStringPreference(Constants.PREF_KEY_PASS)

        if (email != null && password != null) {
            return Pair(email, password)
        }
        return null
    }

    private fun saveSimpleCredentials(mail: String, pass: String) {
        preferencesHelper.setStringPreference(Constants.PREF_KEY_MAIL, mail)
        preferencesHelper.setStringPreference(Constants.PREF_KEY_PASS, pass)
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

class LoginFailedException: RuntimeException {
    constructor(): super()
    constructor(message: String): super(message)
}