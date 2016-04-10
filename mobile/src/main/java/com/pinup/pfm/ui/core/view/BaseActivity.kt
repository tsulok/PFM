package com.pinup.pfm.ui.core.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.ui.ActivityModule

/**
 * The base activity
 */
abstract class BaseActivity : AppCompatActivity() {

    abstract fun getActivityMainContainer(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PFMApplication.injectActivityInjector(this)
        injectActivityComponent()
    }

    /**
     * All activites must inject themselves
     */
    abstract fun injectActivityComponent()

    /**
     * Switches fragment to the new one
     * @param fragment The new fragment to be loaded
     * @param finishCurrentFragment Flag indicates whether the current can be finished or not
     */
    fun switchToFragment(fragment: Fragment, finishCurrentFragment: Boolean = false) {
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
            transaction.commitAllowingStateLoss()
        }

        // TODO set title
//        setTitleByTag(fragment.tag, null)
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

    override fun onDestroy() {
        PFMApplication.resetActivityInjector()
        super.onDestroy()
    }
}

