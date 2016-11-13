package com.pinup.pfm.ui.core.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.orhanobut.logger.Logger

/**
 * Abstract class for all fragment
 */
abstract class BaseFragment : Fragment, IBaseFragment, IFragmentFactory {

    constructor() : super() {
        Logger.d("Base constructor")
//        PFMApplication.activityInjector?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(getLayoutId(), container, false)
        ButterKnife.bind(this, view);

        view?.post {
            getPresenter()?.bind(getScreen())
            initObjects(view)
            initEventHandlers(view)
        }

        return view
    }

    override fun onDestroyView() {
        getPresenter()?.unbind()
        super.onDestroyView()
    }

    /**
     * Indicates whether the fragment uses it's own back stack
     *
     * @return True if handled itself, false otherwise
     */
    fun onBackPressed(): Boolean {
        return false;
    }

    /**
     * Finish this fragment and remove from backstack
     */
    fun finish() {
        activity.supportFragmentManager.popBackStackImmediate();
    }

    override fun getFragment(): BaseFragment {
        return this
    }
}