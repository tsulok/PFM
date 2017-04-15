package com.pinup.pfm.ui.settings

import android.content.Intent
import android.view.View
import com.pinup.pfm.R
import com.pinup.pfm.common.ui.core.BaseScreen
import com.pinup.pfm.di.component.PFMFragmentComponent
import com.pinup.pfm.extensions.string
import com.pinup.pfm.ui.auth.login.LoginActivity
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.common.ui.core.IBasePresenter
import com.pinup.pfm.ui.main_navigator.MainNavigatorFragment
import com.pinup.pfm.utils.helper.UIHelper
import com.pinup.pfm.utils.ui.core.AlertHelper
import kotlinx.android.synthetic.main.settings_fragment.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Fragment for settings
 */
class SettingsFragment : BaseFragment(), SettingsScreen {

    @Inject lateinit var presenter: SettingsPresenter
    @Inject lateinit var alertHelper: AlertHelper

    override fun getLayoutId(): Int {
        return R.layout.settings_fragment
    }

    override fun getPresenter(): IBasePresenter? = presenter
    override fun getScreen(): BaseScreen = this

    override fun injectFragment(component: PFMFragmentComponent) {
        component.inject(this)
    }

    override fun initObjects(view: View?) {
        presenter.initView()
    }

    override fun initEventHandlers(view: View?) {
        settingsMoveMainBtn.setOnClickListener { presenter.onMainNavigationButtonClicked() }
        settingsLogoutBtn.setOnClickListener { presenter.onLogoutClicked(false) }
        settingsSyncBtn.setOnClickListener { presenter.onSyncButtonClicked() }
        settingsCurrencyBtn.setOnClickListener { presenter.listSelectedCurrencies() }
    }

    override fun navigateToMain() {
        (parentFragment as? MainNavigatorFragment)?.navigateToMain()
    }

    override fun askUserConfirmLogout() {
        alertHelper.createAlert(R.string.settings_logout_confirm_title, R.string.settings_logout_confirm_body)
                .positiveText(R.string.settings_logout_action_logout)
                .onPositive { _, _ ->  presenter.onLogoutClicked(true) }
                .negativeText(R.string.settings_logout_action_cancel)
                .onNegative { dialog, _ -> dialog.dismiss() }
                .show()
    }

    private fun navigateToLoginScreen() {
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    override fun logoutUser() {
        navigateToLoginScreen()
    }

    override fun loadingStarted() {
        alertHelper.showProgressDialog(R.string.settings_sync_in_progress)
    }

    override fun loadingFinished() {
        alertHelper.hideProgressDialog()
    }

    override fun updateFailed() {
        alertHelper.createAlert(messageId = R.string.settings_sync_update_failed)
                .positiveText(R.string.retry)
                .onPositive { _, _ -> presenter.onSyncButtonClicked() }
                .negativeText(R.string.cancel)
                .show()
    }

    override fun initView(settingsViewModel: ISettingsViewModel) {
        updateLastSyncTime(settingsViewModel.lastSyncTime)
        settingsUserMailTxt.text = settingsViewModel.userEmail
    }

    override fun updateLastSyncTime(date: Date?) {
        val dateFormatter = SimpleDateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
        val syncText: String
        if (date != null) {
            syncText = dateFormatter.format(date)
        } else {
            syncText = context.string(R.string.settings_sync_last_not_yet)
        }
        settingsSyncTimeTxt.text = context.getString(R.string.settings_sync_last, syncText)
    }

    override fun showCurrencySelector(currencies: List<Currency>, selectedIndex: Int) {
        UIHelper.instance.createDefaultDialog(activity)
                .title(R.string.input_currency_chooser)
                .items(currencies)
                .itemsCallbackSingleChoice(selectedIndex) { _, _, which, _ ->
                    presenter.updateSelectedCurrency(currencies[which])
                    true
                }
                .show()
    }

    override fun updateSelectedCurrency(currency: Currency?) {
        if (currency == null) {
            settingsCurrencyBtn.text = context.string(R.string.settings_currency_no_default_action)
        } else {
            settingsCurrencyBtn.text = currency.displayName
        }
    }
}

interface ISettingsViewModel {
    val lastSyncTime: Date? get
    val userEmail: String get
}