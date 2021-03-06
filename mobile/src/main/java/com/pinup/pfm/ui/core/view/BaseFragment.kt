package com.pinup.pfm.ui.core.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.di.component.DaggerPFMFragmentComponent
import com.pinup.pfm.di.component.PFMFragmentComponent
import com.pinup.pfm.di.module.FragmentModule
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

/**
 * Abstract class for all fragment
 */
abstract class BaseFragment : Fragment(), IBaseFragment, IFragmentFactory {

    @Inject lateinit var eventBus: EventBus

    private val fragmentComponent: PFMFragmentComponent by lazy {
        DaggerPFMFragmentComponent.builder()
                .fragmentModule(FragmentModule(this))
                .pFMApplicationComponent(PFMApplication.applicationComponent)
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectFragment(fragmentComponent)
    }

    override fun onStart() {
        super.onStart()
        if (isEventBusEnabled()) {
            eventBus.register(this)
        }
    }

    override fun onStop() {
        super.onStop()

        if (isEventBusEnabled()) {
            eventBus.unregister(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(getLayoutId(), container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        view?.post {
            getPresenter()?.bind(getScreen())
            initObjects(view)
            initEventHandlers(view)
//        }
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
        return false
    }

    /**
     * Handles the component to resolve the injection
     *
     * @param component The registered component to this fragment
     */
    protected abstract fun injectFragment(component: PFMFragmentComponent)

    protected open fun isEventBusEnabled(): Boolean = false

    /**
     * Finish this fragment and remove from backstack
     */
    fun finish() {
        activity.onBackPressed()
//        activity.supportFragmentManager.popBackStack()
    }

    override fun getFragment(): BaseFragment {
        return this
    }

    val baseActivity: BaseActivity
        get() = activity as BaseActivity
}