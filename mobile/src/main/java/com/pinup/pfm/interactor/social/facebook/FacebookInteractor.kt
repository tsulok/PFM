package com.pinup.pfm.interactor.social.facebook

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.orhanobut.logger.Logger
import com.pinup.pfm.di.qualifiers.Facebook
import com.pinup.pfm.domain.manager.preferences.SharedPreferencesManager
import com.pinup.pfm.interactor.social.facebook.model.FacebookAccessTokenMissingException
import com.pinup.pfm.interactor.social.facebook.model.FacebookDataWrapper
import com.pinup.pfm.interactor.social.facebook.model.FacebookGraphUser
import com.pinup.pfm.interactor.social.facebook.model.FacebookLoginCancelledException
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import java.util.*
import javax.inject.Inject


/**
 * Interactor for facebook components
 */
class FacebookInteractor
@Inject constructor(@Facebook private val callbackManager: CallbackManager,
                    @Facebook private val loginManager: LoginManager,
                    private val preferencesHelper: SharedPreferencesManager) : IFacebookInteractor {

    private object Constants {
        internal val facebookPermissions = Arrays.asList("public_profile", "email")
        internal val facebookToken = "munchkin.facebook.token"
        internal val facebookEmail = "munchkin.facebook.email"
        internal val facebookName = "munchkin.facebook.name"
        internal val facebookId = "munchkin.facebook.id"
    }

    init {
        configure()
    }

    private lateinit var facebookLoginListener: IFacebookLoginListener

    private fun configure() {
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                facebookLoginListener.onSucceeded(loginResult)
            }

            override fun onCancel() {
                facebookLoginListener.onCancelled()
            }

            override fun onError(error: FacebookException) {
                facebookLoginListener.onError(error)
            }
        })
    }

    override fun authorizeUser(activity: Activity): Completable {
        return Completable.create { emitter ->
            facebookLoginListener = object : IFacebookLoginListener {
                override fun onError(error: FacebookException) {
                    emitter.onError(error)
                }

                override fun onCancelled() {
                    emitter.onError(FacebookLoginCancelledException())
                }

                override fun onSucceeded(loginResult: LoginResult) {
                    val accessToken = loginResult.accessToken

                    if (accessToken == null) {
                        emitter.onError(FacebookAccessTokenMissingException())
                        return
                    }

                    getUserData(accessToken)
                            .subscribeOn(Schedulers.io())
                            .subscribe({ facebookGraphUser ->
                                Logger.d("Facebook e-mail address: %1\$s", facebookGraphUser.email)

                                saveFacebookData(
                                        String.format("%1\$s %2\$s", facebookGraphUser.firstName, facebookGraphUser.lastName),
                                        accessToken.token,
                                        accessToken.userId,
                                        facebookGraphUser.email)

                                emitter.onComplete()
                            }, { throwable ->
                                Logger.e("Facebook profile data fetch failed")
                                emitter.onError(throwable)
                            }
                            )
                }
            }

            loginManager.logInWithReadPermissions(activity, Constants.facebookPermissions)
        }
    }

    override fun resetFacebookCredentials() {
        loginManager.logOut()
        preferencesHelper.clearPreference(Constants.facebookEmail)
        preferencesHelper.clearPreference(Constants.facebookToken)
        preferencesHelper.clearPreference(Constants.facebookName)
        preferencesHelper.clearPreference(Constants.facebookId)
    }

    /**
     * Saves the facebook data

     * @param name  Name of the facebook user
     * *
     * @param token Token of the facebook user
     * *
     * @param id    Id of the facebook user
     * *
     * @param email Email of the facebook user
     */
    private fun saveFacebookData(name: String, token: String, id: String, email: String?) {
        preferencesHelper.setStringPreference(Constants.facebookName, name)
        preferencesHelper.setStringPreference(Constants.facebookToken, token)
        preferencesHelper.setStringPreference(Constants.facebookId, id)
        preferencesHelper.setStringPreference(Constants.facebookEmail, email)
    }

    override val storedFacebookData: FacebookDataWrapper?
        get() {
            val name = preferencesHelper.getStringPreference(Constants.facebookName)
            val token = preferencesHelper.getStringPreference(Constants.facebookToken)
            val id = preferencesHelper.getStringPreference(Constants.facebookId)
            val email = preferencesHelper.getStringPreference(Constants.facebookEmail)

            if (name == null || token == null || id == null || email == null) {
                return null
            }

            return FacebookDataWrapper(email, name, token, id)
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Get user sensitive data from facebook user

     * @param accessToken The access token
     * *
     * @return An observable of the user data response
     */
    private fun getUserData(accessToken: AccessToken?): Observable<FacebookGraphUser> {
        return Observable.create({ subscriber ->

            if (accessToken == null) {
                subscriber.onError(FacebookAccessTokenMissingException())
                return@create
            }

            val graphRequest = GraphRequest.newMeRequest(accessToken) { `object`, response ->
                Logger.d("Facebook graph response: %1\$s", response.toString())
                try {
                    val user = FacebookGraphUser(`object`)
                    subscriber.onNext(user)
                    subscriber.onComplete()
                } catch (e: JSONException) {
                    subscriber.onError(e)
                }
            }
            val parameters = Bundle()
            parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location")
            graphRequest.parameters = parameters
            graphRequest.executeAsync()
        })
    }

    /**
     * Private listeners for facebook login
     */
    interface IFacebookLoginListener {
        fun onError(error: FacebookException)

        fun onCancelled()

        fun onSucceeded(loginResult: LoginResult)
    }
}

/**
 * Supported actions on facebook interactor
 */
interface IFacebookInteractor {

    /**
     * Authorize the user with a new facebook connection flow

     * @param activity The source activity
     * *
     * @return An observable with the results
     */
    fun authorizeUser(activity: Activity): Completable

    /**
     * Read the stored facebook data
     * @return The stored facebook data or null if not available
     */
    val storedFacebookData: FacebookDataWrapper?

    /**
     * Need to be get called from activity
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    /**
     * Reset stored facebook credentials
     */
    fun resetFacebookCredentials()
}
