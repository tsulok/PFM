package com.pinup.pfm.utils.ui.core

import android.app.ProgressDialog
import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.pinup.pfm.R
import com.pinup.pfm.di.qualifiers.ActivityContext
import javax.inject.Inject


/**
 * Alert helper
 */
open class AlertHelper @Inject constructor(@ActivityContext val context: Context) {

    private var progressDialog: MaterialDialog? = null

    fun createDefaultLoadingProgressDialog(messageId: Int): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage(context.getString(messageId))
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.setCancelable(false)
        return progressDialog
    }

    fun showProgressDialog() {
        showProgressDialog(R.string.loading)
    }

    fun showProgressDialog(messageId: Int) {
        if (progressDialog == null) {
            progressDialog = createDefaultLoadingMaterialDialog(messageId)
        }
        progressDialog!!.setContent(messageId)

        progressDialog!!.show()
    }

    fun hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog!!.hide()
        }
    }

    /**
     * Creates a defailt loading progress dialog
     * @param messageId The resource id of the message
     * *
     * @return A progress dialog
     */
    fun createDefaultLoadingMaterialDialog(messageId: Int): MaterialDialog {
        return MaterialDialog.Builder(context)
                .content(messageId)
                .progress(true, 0)
                .cancelable(false)
                .titleColor(context.resources.getColor(R.color.colorPrimaryDark))
                .contentColor(context.resources.getColor(R.color.colorPrimaryDark))
                .build()
    }

    /**
     * Creates a default alert dialog with a dismiss button

     * @return The builder of the alert
     */
    fun createDefaultDialog(): MaterialDialog.Builder {
        return MaterialDialog.Builder(context)
                .titleColor(context.resources.getColor(R.color.colorPrimaryDark))
                .contentColor(context.resources.getColor(R.color.colorPrimaryDark))
                .negativeText(R.string.got_it)
                .onNegative({ dialog, which -> dialog.dismiss() })
    }

    /**
     * Creates a default alert dialog with a cancel button and a title

     * @param title    Title of the alert
     * *
     * @return The builder of the alert
     */
    fun createAlert(title: String): MaterialDialog.Builder {
        return createDefaultDialog().title(title)
    }

    /**
     * Creates a default alert dialog with a cancel button with a title and a message

     * @param title    Title of the alert
     * *
     * @param message  Message of the alert
     * *
     * @return The builder of the alert
     */
    fun createAlert(title: String, message: String): MaterialDialog.Builder {
        return createDefaultDialog().title(title).content(message)
    }

    /**
     * Creates a default alert dialog with a cancel button with a title and a message

     * @param titleId   TitleId of the alert
     * *
     * @param messageId MessageId of the alert
     * *
     * @return The builder of the alert
     */
    fun createAlert(titleId: Int, messageId: Int): MaterialDialog.Builder {
        return createDefaultDialog()
                .title(context.resources.getString(titleId))
                .content(context.resources.getString(messageId))
    }
}