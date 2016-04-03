package com.pinup.pfm.test.utils

import com.pinup.pfm.DaggerTestComponent
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.PFMApplicationComponent
import com.pinup.pfm.TestModule
import com.pinup.pfm.interactor.InteractorModule
import org.robolectric.RuntimeEnvironment
import org.robolectric.shadows.ShadowLog

/**
 * Base test
 */
open class BaseTest {

    protected fun setTestInjector() {
        ShadowLog.stream = System.out
        val application: PFMApplication = RuntimeEnvironment.application as PFMApplication
        val injector: PFMApplicationComponent = DaggerTestComponent.builder()
                .testModule(TestModule(application))
                .interactorModule(InteractorModule())
                .build()
        application.setCustomInjector(injector)
    }
}