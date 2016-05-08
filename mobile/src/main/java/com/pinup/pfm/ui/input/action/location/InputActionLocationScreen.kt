package com.pinup.pfm.ui.input.action.location

import com.google.android.gms.maps.model.LatLng
import com.pinup.pfm.ui.core.view.BaseScreen

/**
 * Screen actions for input action location
 */
interface InputActionLocationScreen : BaseScreen {

    fun updateUserLocation(latLng: LatLng, shouldUpdateCameraToPosition: Boolean)
}