package com.pinup.pfm.domain.manager.auth

import com.pinup.pfm.domain.manager.preferences.SharedPreferencesManager
import com.pinup.pfm.interactor.social.facebook.IFacebookInteractor
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manager for authentication
 */
@Singleton
class AuthenticationManager
@Inject constructor(val sharedPreferences: SharedPreferencesManager,
                    val facebookInteractor: IFacebookInteractor) : IAuthenticationManager {

    object Constants {
        @JvmStatic val PREF_KEY_TOKEN = "pfm.token"
        @JvmStatic val PREF_KEY_CURRENT_USER_ID = "pfm.current.userid";
    }


    override var token: String? = sharedPreferences.getStringPreference(Constants.PREF_KEY_TOKEN)

    override var currentUserId: String? = sharedPreferences.getStringPreference(Constants.PREF_KEY_CURRENT_USER_ID)

    override fun storeToken(token: String?) {
        sharedPreferences.setStringPreference(Constants.PREF_KEY_TOKEN, token)
    }

    override fun storeCurrentUserId(id: String?) {
        sharedPreferences.setStringPreference(Constants.PREF_KEY_CURRENT_USER_ID, id)
    }

    override fun clearCredentials() {
        sharedPreferences.clearPreference(Constants.PREF_KEY_CURRENT_USER_ID)
        sharedPreferences.clearPreference(Constants.PREF_KEY_TOKEN)
        facebookInteractor.resetFacebookCredentials()
    }
}

interface IAuthenticationManager {

    var token: String? get
    var currentUserId: String? get

    fun storeToken(token: String?)
    fun storeCurrentUserId(id: String?)

    fun clearCredentials()
}