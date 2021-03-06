package com.pinup.pfm.interactor.user

import android.Manifest
import android.accounts.AccountManager
import android.content.Context
import android.content.SharedPreferences
import com.google.android.gms.auth.GoogleAuthUtil
import com.pinup.pfm.di.qualifiers.ApplicationContext
import com.pinup.pfm.domain.network.dto.user.EditUserDTO
import com.pinup.pfm.domain.network.dto.user.RegisterUserDTO
import com.pinup.pfm.domain.network.service.AuthService
import com.pinup.pfm.domain.network.service.UserService
import com.pinup.pfm.extensions.isPermissionsGranted
import com.pinup.pfm.interactor.auth.AuthInteractor
import io.reactivex.Completable
import javax.inject.Inject

/**
 * User specific interactor
 */

class UserInteractor
@Inject constructor(@ApplicationContext private val context: Context,
                    private val accountManager: AccountManager,
                    private val userService: UserService): IUserInteractor {

    override fun getAccounts(): List<String> {
        if (context.isPermissionsGranted(Manifest.permission.GET_ACCOUNTS)) {
            val accounts = accountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE)
            val emails: MutableList<String> = ArrayList()
            accounts.mapTo(emails) { it.name }
            return emails
        }

        return ArrayList()
    }

    override fun signUp(email: String, password: String): Completable {
        val request = RegisterUserDTO(email, password)
        return Completable.fromObservable(userService.register(request))
    }

    override fun update(name: String, currency: String): Completable {
        val request = EditUserDTO(name, currency)
        return Completable.fromObservable(userService.update(request))
    }
}

interface IUserInteractor {
    /**
     * List currently available google accounts located on the device
     *
     * @return The email addresses of the found users
     */
    fun getAccounts(): List<String>

    /**
     * This will signup a user
     */
    fun signUp(email: String, password: String): Completable

    /**
     * This will update a user
     */
    fun update(name: String, currency: String): Completable
}