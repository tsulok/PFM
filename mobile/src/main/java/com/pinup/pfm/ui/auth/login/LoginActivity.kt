package com.pinup.pfm.ui.auth.login

import android.Manifest
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import com.orhanobut.logger.Logger
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
import permissions.dispatcher.*
import javax.inject.Inject


/**
 * Activity for login
 */
@RuntimePermissions
class LoginActivity : BaseActivity(), LoginScreen {

    @Inject lateinit var presenter: LoginPresenter
    @Inject lateinit var facebookInteractor: IFacebookInteractor
    @Inject lateinit var alertHelper: AlertHelper

    override fun getActivityMainContainer(): Int = 0 // No such container

    override fun loadContentId(): Int = R.layout.login_activity

    override fun initObjects() {
        initEventHandlers()
        LoginActivityPermissionsDispatcher.configureAutocompleteForEmailsWithCheck(this)
        presenter.reauthUserIfPossible()
    }

    override fun injectActivity(component: PFMActivityComponent) {
        component.inject(this)
    }

    override fun getPresenter(): IBasePresenter? = presenter

    override fun getScreen(): BaseScreen = this

    private fun initEventHandlers() {
        loginBtn.setOnClickListener {
            presenter.loginWithMail(emailETxt.text.toString(),
                    passwordETxt.text.toString())
        }

        registerBtn.setOnClickListener {
            presenter.registerUser(emailETxt.text.toString(),
                    passwordETxt.text.toString())
        }

        loginFacebookBtn.setOnClickListener {
            facebookInteractor.authorizeUser(this)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ ->
                        presenter.loginWithFacebook()
                    }, {
                        alertHelper.createAlert(string(R.string.login_facebook_failed)).show()
                    })
        }

        emailETxt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if ("@test" == s.toString()) {
                    emailETxt.setText("t.david92@gmail.com")
                    passwordETxt.setText("Abcd1234.")
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        facebookInteractor.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        LoginActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults)
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

    @NeedsPermission(Manifest.permission.GET_ACCOUNTS)
    fun configureAutocompleteForEmails() {
        val accounts = presenter.collectInstalledGoogleAccounts()

        val accountsAdapter = ArrayAdapter<String>(this, R.layout.email_autocomplete, accounts)
        emailETxt.setAdapter(accountsAdapter)
    }

    @OnShowRationale(Manifest.permission.GET_ACCOUNTS)
    fun showRationaleForAccounts(request: PermissionRequest) {
        alertHelper.createAlert(R.string.permission_accounts_title,
                R.string.permission_accounts_rationale)
                .positiveText(R.string.grant)
                .negativeText(R.string.decline)
                .onPositive({ _, _ -> request.proceed() })
                .onNegative({ _, _ -> request.cancel() })
                .show()
    }

    @OnPermissionDenied(Manifest.permission.GET_ACCOUNTS)
    fun showDeniedForAccounts() {
        Logger.d("Accounts never denied")
    }

    @OnNeverAskAgain(Manifest.permission.GET_ACCOUNTS)
    fun showNeverAskForAccounts() {
        Logger.d("Accounts never ask again")
    }

    override fun hideInputErrors() {
        emailInputContainer.isErrorEnabled = false
        passwordInputContainer.isErrorEnabled = false
    }

    override fun onMailNotValid() {
        emailInputContainer.error = this.string(R.string.login_email_validation)
    }

    override fun onPasswordNotValid() {
        passwordInputContainer.error = this.string(R.string.login_password_validation)
    }

    override fun loginFailed() {
        alertHelper.createAlert(R.string.error_general_title,
                R.string.login_error_fail)
                .show()
    }

    override fun registrationFailed() {
        alertHelper.createAlert(R.string.error_general_title,
                R.string.login_registration_fail)
                .show()
    }

    override fun forgotPasswordEmailNotValid() {
        alertHelper.createAlert(R.string.error_general_title,
                R.string.login_email_validation)
                .show()
    }
}