package com.pinup.pfm.utils.helper

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.pinup.pfm.R

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

    /**
     * Creates a default alert dialog with a cancel button
     *
     * @param activity The parent activity of the alert
     * @return The builder of the alert
     */
    fun createDefaultDialog(activity: Activity): MaterialDialog.Builder {
        return MaterialDialog.Builder(activity)
                .negativeText(android.R.string.cancel)
                .titleColor(activity.resources.getColor(R.color.colorPrimaryDark))
                .contentColor(activity.resources.getColor(R.color.colorPrimaryDark))
                .onNegative { materialDialog, _ -> materialDialog.dismiss() }
    }

    /**
     * Creates a default alert dialog with a cancel button and a title
     *
     * @param activity The parent activity of the alert
     * @param title    Title of the alert
     * @return The builder of the alert
     */
    fun createAlert(activity: Activity, title: String): MaterialDialog.Builder {
        return createDefaultDialog(activity).title(title)
    }

    /**
     * Creates a default alert dialog with a cancel button with a title and a message
     *
     * @param activity The parent activity of the alert
     * @param title    Title of the alert
     * @param message  Message of the alert
     * @return The builder of the alert
     */
    fun createAlert(activity: Activity, title: String, message: String): MaterialDialog.Builder {
        return createDefaultDialog(activity).title(title).content(message)
    }

    /**
     * Creates a default alert dialog with a cancel button with a title and a message
     *
     * @param activity  The parent activity of the alert
     * @param titleId   TitleId of the alert
     * @param messageId MessageId of the alert
     * @return The builder of the alert
     */
    fun createAlert(activity: Activity, titleId: Int, messageId: Int): MaterialDialog.Builder {
        return createDefaultDialog(activity)
                .title(activity.resources.getString(titleId))
                .content(activity.resources.getString(messageId))
    }
}