package com.pinup.pfm.ui.input.action.location

import com.google.android.gms.maps.model.LatLng
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.interactor.transaction.CurrentTransactionInteractor
import com.pinup.pfm.ui.core.view.BasePresenter
import javax.inject.Inject

/**
 * Presenter for input action location
 */
class InputActionLocationPresenter : BasePresenter<InputActionLocationScreen> {

    @Inject lateinit var currentTransactionInteractor: CurrentTransactionInteractor

    private var userLocation: LatLng? = null
    private var userMarkerPosition: LatLng? = null

    constructor() : super() {
        PFMApplication.injector.inject(this)
        userMarkerPosition = currentTransactionInteractor.transactionLocation
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
        currentTransactionInteractor.transactionLocation = userMarkerPosition
    }

    /**
     * Initializes default marker
     */
    fun initDefaultMarker() {
        var desiredMarkerPosition: LatLng

        if (userMarkerPosition != null) {
            desiredMarkerPosition = userMarkerPosition!!
        } else if (userLocation != null) {
            desiredMarkerPosition = userLocation!!
        } else {
            throw RuntimeException("User position is not known. Must be set before initializing markers." +
                    " Developer error")
        }

        screen?.createMarkerInLocation(desiredMarkerPosition)
    }
}