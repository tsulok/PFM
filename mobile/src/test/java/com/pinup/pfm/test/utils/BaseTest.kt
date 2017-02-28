package com.pinup.pfm.test.utils

import com.pinup.pfm.DaggerTestComponent
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.di.component.PFMApplicationComponent
import com.pinup.pfm.di.module.ApplicationModule
import com.pinup.pfm.test.di.module.TestModule
import org.robolectric.RuntimeEnvironment
import org.robolectric.shadows.ShadowLog

/**
 * Base test
 */
open class BaseTest {

    protected fun setTestInjector() {
        ShadowLog.stream = System.out
        val application: PFMApplication = RuntimeEnvironment.application as PFMApplication
        val applicationComponent: PFMApplicationComponent = DaggerTestComponent.builder()
                .applicationModule(ApplicationModule(application))
                .testModule(TestModule(application))
                .build()
        application.setCustomInjector(applicationComponent)
    }
}