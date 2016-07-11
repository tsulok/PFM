package com.pinup.pfm.test

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.pinup.pfm.R
import org.jetbrains.anko.find

/**
 * Test fragment
 */
class TestFragment : Fragment() {

    val testBtn by lazy { find<Button>(R.id.buttonTest) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(getLayoutId(), container, false)

        view?.post { initObjects(view) }
        return view
    }

    fun getLayoutId(): Int {
        return R.layout.fragment_test
    }

    fun initObjects(view: View?) {
        testBtn.text = "körtefa"
    }

    fun initEventHandlers(view: View?) {

    }
}