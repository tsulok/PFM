package com.pinup.pfm.utils.helper

import android.content.Context
import android.widget.Toast

/**
 * UIHelper for ui components
 */
enum class UIHelper {
    instance;

    var toast: Toast? = null

    fun makeToast(context: Context, message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast?.show()
    }
}