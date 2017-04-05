package com.pinup.pfm.extensions

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.widget.Toast
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.utils.helper.UIHelper

/**
 * Extension functions for fragments
 */

fun BaseFragment.replaceFragment(fragmentManager: FragmentManager, containerId: Int, fragment: BaseFragment, tag: String? = null) {
    fragmentManager
            .beginTransaction()
            .replace(containerId, fragment, tag)
            .commitAllowingStateLoss()
    fragmentManager.executePendingTransactions()
}

fun BaseFragment.replaceFragment(fragmentManager: FragmentManager, containerId: Int, tag: String? = null) {
    fragmentManager
            .beginTransaction()
            .replace(containerId, this, tag)
            .commitAllowingStateLoss()
    fragmentManager.executePendingTransactions()
}

fun Fragment.makeToast(stringResourceId: Int) {
    UIHelper.instance.makeToast(context, resources.getString(stringResourceId))
}

fun Fragment.makeToast(message: String) {
    UIHelper.instance.makeToast(context, message)
}