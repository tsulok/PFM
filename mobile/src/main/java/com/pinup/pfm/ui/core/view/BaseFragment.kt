package com.pinup.pfm.ui.core.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.orhanobut.logger.Logger
import com.pinup.pfm.PFMApplication
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

/**
 * Abstract class for all fragment
 */
abstract class BaseFragment : Fragment, IBaseFragment, IFragmentFactory {

//    @Inject lateinit var bus: EventBus

    constructor() : super() {
        Logger.d("Base fragment ctor")
//        PFMApplication.activityInjector?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(getLayoutId(), container, false)
        ButterKnife.bind(this, view);

        initObjects(view)
        initEventHandlers(view)

        return view
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