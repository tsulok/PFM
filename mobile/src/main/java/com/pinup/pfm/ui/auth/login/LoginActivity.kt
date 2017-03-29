package com.pinup.pfm.ui.auth.login

import android.content.Intent
import com.pinup.pfm.R
import com.pinup.pfm.di.component.PFMActivityComponent
import com.pinup.pfm.ui.core.view.BaseActivity
import com.pinup.pfm.ui.core.view.BaseScreen
import com.pinup.pfm.ui.core.view.IBasePresenter
import com.pinup.pfm.ui.main.MainActivity
import kotlinx.android.synthetic.main.login_activity.*
import javax.inject.Inject

/**
 * Activity for login
 */
class LoginActivity : BaseActivity(), LoginScreen {

    @Inject lateinit var presenter: LoginPresenter

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
    }
}