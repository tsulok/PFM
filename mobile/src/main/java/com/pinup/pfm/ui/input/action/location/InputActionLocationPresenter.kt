package com.pinup.pfm.ui.input.action.location

import com.google.android.gms.maps.model.LatLng
import com.patloew.rxlocation.RxLocation
import com.pinup.pfm.domain.manager.transaction.ITransactionManager
import com.pinup.pfm.ui.core.view.BasePresenter
import javax.inject.Inject

/**
 * Presenter for input action location
 */
class InputActionLocationPresenter @Inject constructor(val transactionManager: ITransactionManager,
                                                       val rxLocation: RxLocation) : BasePresenter<InputActionLocationScreen>() {

    private var userLocation: LatLng? = null
    private var userMarkerPosition: LatLng? = null

    init {
        userMarkerPosition = transactionManager.transactionLocation
    }

    fun loadCurrentLocation() {
        rxLocation.location()
                .lastLocation()
                .subscribe({ location ->
                    userLocation = LatLng(location.latitude, location.longitude)
                    initDefaultMarker()
                }, { error ->
                    screen?.locationNotFound()
                })
    }

    /**
     * Updates the user's current location
     * @param latLng The new position
     * @param forceMoveToUserLocation Flag whether the ui should moved to the user's location
     */
    fun updateUserLocation(latLng: LatLng, forceMoveToUserLocation: Boolean = false) {
        userLocation = latLng
        if (forceMoveToUserLocation || userMarkerPosition == null) {
            if (userLocation != null) {
                screen?.moveToUserLocation(userLocation!!)
            }
        } else if (userMarkerPosition != null) {
            screen?.moveToUserMarkerLocation(userMarkerPosition!!)
        }
    }

    /**
     * Updates user's marker position
     */
    fun updateUserMarkerPosition(latLng: LatLng?) {
        this.userMarkerPosition = latLng
        transactionManager.transactionLocation = userMarkerPosition
    }

    /**
     * Initializes default marker
     */
    fun initDefaultMarker() {
        val location = userMarkerPosition?.let {
            screen?.createMarkerInLocation(it)
            screen?.moveToUserLocation(it)
        }

        if (location == null) {
            userLocation?.let {
                screen?.createMarkerInLocation(it)
                screen?.moveToUserLocation(it)
            }
        }
    }
}