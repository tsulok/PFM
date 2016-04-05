package com.pinup.pfm.ui.core.view

/**
 * Abstract presenter class
 */
open class BasePresenter<T : BaseScreen> {

    protected var screen: T? = null

    open protected fun bind(screen: T) {
        this.screen = screen
    }

    open protected fun unbind() {
        this.screen = null
    }

}