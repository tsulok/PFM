package com.pinup.pfm.ui.core.view

/**
 * Abstract presenter class
 */
open class BasePresenter<T : BaseScreen> {

    protected var screen: T? = null

    open fun bind(screen: T) {
        this.screen = screen
    }

    open fun unbind() {
        this.screen = null
    }

}