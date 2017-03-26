package com.pinup.pfm.ui.input.action.location

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.pinup.pfm.R
import com.pinup.pfm.di.component.PFMFragmentComponent
import com.pinup.pfm.extensions.makeToast
import com.pinup.pfm.ui.core.view.BaseMapFragment
import com.pinup.pfm.ui.core.view.BaseScreen
import com.pinup.pfm.ui.core.view.IBasePresenter
import javax.inject.Inject

/**
 * Input action location fragment
 */
class InputActionLocationFragment : BaseMapFragment(), InputActionLocationScreen {

    val DEFAULT_ZOOM_LEVEL: Float = 16.0f

    @Inject lateinit var inputActionLocationPresenter: InputActionLocationPresenter

    private lateinit var userPositionMarker: Marker

    override fun getPresenter(): IBasePresenter? = inputActionLocationPresenter
    override fun getScreen(): BaseScreen = this

    override fun injectFragment(component: PFMFragmentComponent) {
        component.inject(this)
    }

    override fun onMapInitialized() {
        inputActionLocationPresenter.loadCurrentLocation()
        googleMap.setOnMarkerDragListener(customMarkerDragListener)
    }

    override fun setUpMapSettings() {
        super.setUpMapSettings()
        val settings = googleMap.uiSettings
        settings.isZoomControlsEnabled = false
    }

    //region Screen actions
    override fun moveToUserLocation(latLng: LatLng) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM_LEVEL))
    }

    override fun moveToUserMarkerLocation(latLng: LatLng) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM_LEVEL))
    }

    override fun createMarkerInLocation(latLng: LatLng) {
        googleMap.clear()
        userPositionMarker = googleMap.addMarker(
                MarkerOptions()
                        .draggable(true)
                        .position(latLng)
                        .title(getString(R.string.input_action_location_marker_title)))
        inputActionLocationPresenter.updateUserMarkerPosition(latLng)
    }
    //endregion

    /**
     * Custom listener for handling marker dragging events
     */
    private val customMarkerDragListener = object : GoogleMap.OnMarkerDragListener {

        override fun onMarkerDragEnd(marker: Marker?) {
            inputActionLocationPresenter.updateUserMarkerPosition(marker?.position)
        }

        override fun onMarkerDrag(marker: Marker?) {

        }

        override fun onMarkerDragStart(marker: Marker?) {

        }
    }

    override fun locationNotFound() {
        makeToast(R.string.location_not_found)
    }
}