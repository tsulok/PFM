package com.pinup.pfm.ui.input.action.location

import com.google.android.gms.maps.model.LatLng
import com.pinup.pfm.ui.core.view.BasePresenter

/**
 * Presenter for input action location
 */
class InputActionLocationPresenter : BasePresenter<InputActionLocationScreen> {

    var userLocation: LatLng? = null

    constructor() : super() {

    }

    fun updateUserLocation(latLng: LatLng, shouldUpdatePosition: Boolean = false) {
        userLocation = latLng
        screen?.updateUserLocation(latLng, shouldUpdatePosition)
    }
}