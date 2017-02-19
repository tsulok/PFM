package com.pinup.pfm.ui.core.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.orhanobut.logger.Logger
import com.pinup.pfm.di.component.PFMFragmentComponent

/**
 * Abstract class for all fragment
 */
abstract class BaseFragment : Fragment(), IBaseFragment, IFragmentFactory {

    private lateinit var fragmentComponent: PFMFragmentComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectFragment(fragmentComponent())
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
        return false
    }

    /**
     * Returns with the associated activity component
     *
     * @return The activity component. If not exists creates one.
     */
    protected fun fragmentComponent(): PFMFragmentComponent {
//        if (fragmentComponent == null) {
//            fragmentComponent = PFMFragmentComponent.builder()
//                    .fragmentModule(new FragmentModule(this))
//                    .applicationComponent(MunchkinApplication.get(getContext()).getApplicationComponent())
//                    .build();
//        }
        return fragmentComponent;
    }

    /**
     * Handles the component to resolve the injection
     *
     * @param component The registered component to this fragment
     */
    protected abstract fun injectFragment(component: PFMFragmentComponent)

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