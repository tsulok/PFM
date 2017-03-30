package com.pinup.pfm.ui.auth.login

import android.content.Intent
import com.pinup.pfm.R
import com.pinup.pfm.di.component.PFMActivityComponent
import com.pinup.pfm.extensions.string
import com.pinup.pfm.interactor.social.facebook.IFacebookInteractor
import com.pinup.pfm.ui.core.view.BaseActivity
import com.pinup.pfm.ui.core.view.BaseScreen
import com.pinup.pfm.ui.core.view.IBasePresenter
import com.pinup.pfm.ui.main.MainActivity
import com.pinup.pfm.utils.ui.core.AlertHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.login_activity.*
import javax.inject.Inject

/**
 * Activity for login
 */
class LoginActivity : BaseActivity(), LoginScreen {

    @Inject lateinit var presenter: LoginPresenter
    @Inject lateinit var facebookInteractor: IFacebookInteractor
    @Inject lateinit var alertHelper: AlertHelper

    override fun getActivityMainContainer(): Int = 0 // No such container

    override fun loadContentId(): Int = R.layout.login_activity

    override fun initObjects() {
        initEventHandlers()
    }

    override fun injectActivity(component: PFMActivityComponent) {
        component.inject(this)
    }

    override fun getPresenter(): IBasePresenter? = presenter

    override fun getScreen(): BaseScreen = this

    private fun initEventHandlers() {
        loginBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        loginFacebookBtn.setOnClickListener {
            facebookInteractor.authorizeUser(this)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ ->
                        presenter.loginWithFacebook()
                    }, {
                        alertHelper.createAlert(this.string(R.string.login_facebook_failed)).show()
                    })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        facebookInteractor.onActivityResult(requestCode, resultCode, data)
    }

    override fun loadingStarted() {
        alertHelper.showProgressDialog()
    }

    override fun loadingFinished() {
        alertHelper.hideProgressDialog()
    }

    override fun moveToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}