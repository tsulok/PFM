package com.pinup.pfm.ui.input.action.location

import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.ui.core.view.BaseMapFragment
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import javax.inject.Inject

/**
 * Input action location fragment
 */
class InputActionLocationFragment : BaseMapFragment, InputActionLocationScreen {

    val DEFAULT_ZOOM_LEVEL: Float = 14.0f

    @Inject lateinit var inputActionLocationPresenter: InputActionLocationPresenter
    @Inject lateinit var locationProvider: ReactiveLocationProvider

    constructor() : super() {
        PFMApplication.activityInjector?.inject(this)
    }

    override fun initObjects(view: View?) {
        super.initObjects(view)
        inputActionLocationPresenter.bind(this)
    }

    override fun onDestroyView() {
        inputActionLocationPresenter.unbind()
        super.onDestroyView()
    }

    override fun onMapInitialized() {
        locationProvider.lastKnownLocation.subscribe { location ->
            inputActionLocationPresenter.updateUserLocation(
                    LatLng(location.latitude, location.longitude), true)
        }
    }

    //region Screen actions
    override fun updateUserLocation(latLng: LatLng, shouldUpdateCameraToPosition: Boolean) {
        if (shouldUpdateCameraToPosition) {
            googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM_LEVEL))
        }
    }
    //endregion
}