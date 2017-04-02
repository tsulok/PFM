package com.pinup.pfm.interactor.user

import android.Manifest
import android.accounts.AccountManager
import android.content.Context
import com.google.android.gms.auth.GoogleAuthUtil
import com.pinup.pfm.di.qualifiers.ApplicationContext
import com.pinup.pfm.extensions.isPermissionsGranted
import javax.inject.Inject

/**
 * User specific interactor
 */

class UserInteractor
@Inject constructor(@ApplicationContext val context: Context,
                    val accountManager: AccountManager): IUserInteractor {

    override fun getAccounts(): List<String> {
        if (context.isPermissionsGranted(Manifest.permission.GET_ACCOUNTS)) {
            val accounts = accountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE)
            val emails: MutableList<String> = ArrayList()
            accounts.mapTo(emails) { it.name }
            return emails
        }

        return ArrayList()
    }
}

interface IUserInteractor {
    /**
     * List currently available google accounts located on the device
     *
     * @return The email addresses of the found users
     */
    fun getAccounts(): List<String>
}