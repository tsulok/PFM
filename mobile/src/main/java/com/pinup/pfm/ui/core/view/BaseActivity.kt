package com.pinup.pfm.ui.core.view

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.di.component.DaggerPFMActivityComponent
import com.pinup.pfm.di.component.PFMActivityComponent
import com.pinup.pfm.di.component.PFMFragmentComponent
import com.pinup.pfm.di.module.ActivityModule

/**
 * The base activity
 */
abstract class BaseActivity : AppCompatActivity() {

    private val activityComponent: PFMActivityComponent by lazy {
        DaggerPFMActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .pFMApplicationComponent(PFMApplication.applicationComponent)
                .build()
    }

    abstract fun getActivityMainContainer(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        injectActivity(activityComponent)
        super.onCreate(savedInstanceState)
        setContentView(loadContentId())
        getPresenter()?.bind(getScreen())
        initObjects()
    }

    override fun onStart() {
        super.onStart()
        getPresenter()?.bind(getScreen())
    }

    override fun onStop() {
        getPresenter()?.unbind()
        super.onStop()
    }

    abstract fun loadContentId(): Int
    abstract fun initObjects()

    /**
     * Switches fragment to the new one
     * @param fragment The new fragment to be loaded
     * @param finishCurrentFragment Flag indicates whether the current can be finished or not
     */
    fun switchToFragment(fragment: Fragment, finishCurrentFragment: Boolean = false) {
        switchToFragmentWithTransition(fragment, finishCurrentFragment)
    }

    fun switchToFragmentWithTransition(fragment: Fragment, finishCurrentFragment: Boolean = false, vararg sharedTransactionElementWrapper: SharedTransactionElementWrapper) {
        val fm = supportFragmentManager
        val tag = fragment.javaClass.name

        if (finishCurrentFragment && !isFinishing) {
            fm.popBackStackImmediate()
        }
        val fragmentExists = fm.findFragmentByTag(tag) != null
        if (fragmentExists) {
            fm.popBackStackImmediate(tag, 0)
        } else {
            val transaction = fm.beginTransaction()
            transaction.replace(getActivityMainContainer(), fragment, tag)
            transaction.addToBackStack(tag)

            // Add shared transitions if target is above lollipop and have any
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                for (wrapper in sharedTransactionElementWrapper) {
                    transaction.addSharedElement(
                            wrapper.view,
                            wrapper.identifier)
                }
            }

            transaction.commitAllowingStateLoss()
        }
    }

    /**
     * Returns the currently loaded active fragment if any
     */
    fun getActiveBaseFragment(): BaseFragment? {
        val fragment = supportFragmentManager.findFragmentById(getActivityMainContainer())
        if (fragment is BaseFragment) {
            return fragment
        }

        return null
    }

    /**
     * Handles back press
     * If we run out of fragments (we are on the first page) and press back, quit the app
     */
    override fun onBackPressed() {
        val activeFragment = getActiveBaseFragment()

        // If the active fragment handled it's own back press, do nothing
        if (activeFragment != null && activeFragment.onBackPressed()) {
            return
        }

        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
            return
        }

        super.onBackPressed()
    }

    /**
     * Handles the component to resolve the injection
     *
     * @param component The registered component to this activity
     */
    protected abstract fun injectActivity(component: PFMActivityComponent)

    abstract fun getPresenter(): IBasePresenter?
    abstract fun getScreen(): BaseScreen
}