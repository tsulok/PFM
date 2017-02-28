package com.pinup.pfm.test.utils

import com.pinup.pfm.PFMApplication
import com.pinup.pfm.di.component.PFMApplicationComponent
import org.robolectric.RuntimeEnvironment
import org.robolectric.shadows.ShadowLog

/**
 * Base test
 */
open class BaseTest {

    protected fun setTestInjector() {
        ShadowLog.stream = System.out
        val application: PFMApplication = RuntimeEnvironment.application as PFMApplication
//        val applicationComponent: PFMApplicationComponent = DaggerTestComponent.builder()
//                .testModule(TestModule(application))
//                .interactorModule(InteractorModule())
//                .build()
//        application.setCustomInjector(applicationComponent)
    }
}