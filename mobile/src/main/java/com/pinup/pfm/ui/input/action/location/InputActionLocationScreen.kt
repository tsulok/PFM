package com.pinup.pfm.ui.input.action.location

import com.google.android.gms.maps.model.LatLng
import com.pinup.pfm.common.ui.core.BaseScreen

/**
 * Screen actions for input action location
 */
interface InputActionLocationScreen: BaseScreen {

    fun moveToUserLocation(latLng: LatLng)

    fun moveToUserMarkerLocation(latLng: LatLng)

    fun createMarkerInLocation(latLng: LatLng)

    fun locationNotFound()
}