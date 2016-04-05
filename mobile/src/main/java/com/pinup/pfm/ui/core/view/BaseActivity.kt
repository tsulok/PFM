package com.pinup.pfm.ui.core.view

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
/**
 * The base activity
 */
abstract class BaseActivity : AppCompatActivity() {

    abstract fun getActivityMainContainer(): Int

    /**
     * Switches fragment to the new one
     * @param fragment The new fragment to be loaded
     * @param finishCurrentFragment Flag indicates whether the current can be finished or not
     */
    fun switchToFragment(fragment: Fragment, finishCurrentFragment: Boolean) {
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
}

