package com.pinup.pfm.ui.main

import android.os.Bundle
import com.pinup.pfm.R
import com.pinup.pfm.common.ui.core.BaseScreen
import com.pinup.pfm.di.component.PFMActivityComponent
import com.pinup.pfm.extensions.makeToast
import com.pinup.pfm.ui.core.view.BaseActivity
import com.pinup.pfm.common.ui.core.IBasePresenter
import com.pinup.pfm.ui.main_navigator.MainNavigatorFragment
import com.pinup.pfm.utils.helper.UIHelper
import com.pinup.pfm.utils.ui.core.AlertHelper
import java.util.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainScreen {

    @Inject lateinit var mainPresenter: MainPresenter
    @Inject lateinit var alertHelper: AlertHelper

    val mainNavigatorFragment: MainNavigatorFragment by lazy { MainNavigatorFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getAnalyticsScreenName(): String? = "Transactions"

    override fun loadContentId(): Int = R.layout.activity_main

    override fun initObjects() {
        mainPresenter.initMain()
    }

    override fun injectActivity(component: PFMActivityComponent) {
        component.inject(this)
    }

    override fun getActivityMainContainer(): Int {
        return R.id.containerMain
    }

    override fun loadMainNavigation() {
        switchToFragment(mainNavigatorFragment)
    }

    override fun getPresenter(): IBasePresenter? = mainPresenter

    override fun getScreen(): BaseScreen = this

    override fun loadingStarted() {
        alertHelper.showProgressDialog(R.string.sync_main_content)
    }

    override fun loadingFinished() {
        alertHelper.hideProgressDialog()
    }

    override fun loadInitialDataFailedToLoad() {
        alertHelper.createAlert(messageId = R.string.sync_main_content_failed_mandatory)
                .negativeText(R.string.sync_main_content_cancel)
                .onNegative { dialog, _ ->
                    dialog.dismiss()
                    finish()
                }
                .positiveText(R.string.sync_main_content_retry)
                .onPositive { _, _ -> mainPresenter.initMain() }
                .show()
    }

    override fun syncFailedButHasInitialData() {
        makeToast(R.string.sync_main_content_failed)
    }

    override fun showDefaultCurrencyChooser(currencies: List<Currency>) {
        UIHelper.instance.createDefaultDialog(this)
                .title(R.string.input_currency_chooser)
                .items(currencies)
                .onNegative { _, _ -> loadMainNavigation() }
                .itemsCallbackSingleChoice(-1) { _, _, which, _ ->
                    mainPresenter.updateSelectedCurrency(currencies[which])
                    true
                }
                .show()
    }
}