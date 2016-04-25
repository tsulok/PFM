package com.pinup.pfm.test

import android.transition.ChangeBounds
import android.transition.Scene
import android.transition.TransitionManager
import android.view.View
import android.widget.FrameLayout
import butterknife.Bind
import butterknife.OnClick
import butterknife.bindView
import com.pinup.pfm.R
import com.pinup.pfm.ui.core.view.BaseFragment

/**
 * Test fragment
 */
class TestFragment : BaseFragment() {

//    val sceneRoot: FrameLayout by bindView(R.id.scene_container)
    lateinit var sceneRoot: FrameLayout
    lateinit var scene1: Scene
    lateinit var scene2: Scene
    var sceneState1Applied: Boolean = true

    override fun getLayoutId(): Int {
        return R.layout.fragment_test
    }

    override fun initObjects(view: View?) {

        if (view != null) {
            sceneRoot = view.findViewById(R.id.scene_container) as FrameLayout
        }

        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.layout_test_1, context)
        scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.layout_test_2, context)
    }

    override fun initEventHandlers(view: View?) {

    }

    @OnClick(R.id.buttonTest)
    fun testButtonClicked() {
        val desiredScene = if (sceneState1Applied) scene2 else scene1
        sceneState1Applied = !sceneState1Applied
        TransitionManager.go(desiredScene, ChangeBounds())
    }
}