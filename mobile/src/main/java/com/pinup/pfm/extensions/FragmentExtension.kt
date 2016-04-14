package com.pinup.pfm.extensions

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.widget.Toast
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.utils.helper.UIHelper

/**
 * Extension functions for fragments
 */

fun Fragment.replaceFragment(fragmentManager: FragmentManager, containerId: Int, fragment: BaseFragment, tag: String? = null) {
    fragmentManager
            .beginTransaction()
            .replace(containerId, fragment, tag)
            .commitAllowingStateLoss()
}

fun Fragment.makeToast(stringResourceId: Int) {
    UIHelper.instance.makeToast(context, resources.getString(stringResourceId))
}

fun Fragment.makeToast(message: String) {
    UIHelper.instance.makeToast(context, message)
}