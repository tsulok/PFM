package com.pinup.pfm.ui.input.action.location

import android.Manifest
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.pinup.pfm.R
import com.pinup.pfm.common.ui.core.BaseScreen
import com.pinup.pfm.di.component.PFMFragmentComponent
import com.pinup.pfm.extensions.makeToast
import com.pinup.pfm.ui.core.view.BaseMapFragment
import com.pinup.pfm.common.ui.core.IBasePresenter
import com.pinup.pfm.utils.ui.core.AlertHelper
import kotlinx.android.synthetic.main.fragment_map.*
import permissions.dispatcher.*
import javax.inject.Inject

/**
 * Input action location fragment
 */
@RuntimePermissions
class InputActionLocationFragment : BaseMapFragment(), InputActionLocationScreen {

    val DEFAULT_ZOOM_LEVEL: Float = 16.0f

    @Inject lateinit var inputActionLocationPresenter: InputActionLocationPresenter
    @Inject lateinit var alertHelper: AlertHelper

    private lateinit var userPositionMarker: Marker

    override fun getPresenter(): IBasePresenter? = inputActionLocationPresenter
    override fun getScreen(): BaseScreen = this

    override fun injectFragment(component: PFMFragmentComponent) {
        component.inject(this)
    }

    override fun initEventHandlers(view: View?) {
        super.initEventHandlers(view)

        mapPermissionBtn.setOnClickListener { InputActionLocationFragmentPermissionsDispatcher.loadLocationWithCheck(this) }
    }

    override fun onMapInitialized() {
        googleMap.setOnMarkerDragListener(customMarkerDragListener)
        InputActionLocationFragmentPermissionsDispatcher.loadLocationWithCheck(this)
    }

    override fun setUpMapSettings() {
        super.setUpMapSettings()
        val settings = googleMap.uiSettings
        settings.isZoomControlsEnabled = false
    }

    //region Screen actions
    override fun moveToUserLocation(latLng: LatLng) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM_LEVEL))
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

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun loadLocation() {
        enableLocationSettings()
        mapPermissionBtn.visibility = View.GONE
        inputActionLocationPresenter.loadCurrentLocation()
    }

    //region Permission handling
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        InputActionLocationFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults)
    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    fun showRationaleLocation(request: PermissionRequest) {
        alertHelper.createAlert(R.string.permission_location_title,
                R.string.permission_location_rationale_message)
                .positiveText(R.string.grant)
                .negativeText(R.string.decline)
                .onPositive({ _, _ -> request.proceed() })
                .onNegative({ _, _ -> request.cancel() })
                .show()
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    fun showNeverAskForLocation() {
        alertHelper.createAlert(R.string.permission_location_title,
                R.string.permission_location_neveragain_message)
                .positiveText(R.string.got_it)
                .negativeText(R.string.cancel)
                .show()
    }
    //endregion
}