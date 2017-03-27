package com.pinup.pfm.ui.core.view

import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.pinup.pfm.R
import com.pinup.pfm.extensions.isPermissionsGranted
import kotlinx.android.synthetic.main.fragment_map.*

/**
 * A base fragment for map
 */
abstract class BaseMapFragment: BaseFragment(), OnMapReadyCallback {

    protected lateinit var supportMapFragment: SupportMapFragment
    protected lateinit var googleMap: GoogleMap

    override fun getLayoutId(): Int {
        return R.layout.fragment_map
    }

    override fun initObjects(view: View?) {
        supportMapFragment = SupportMapFragment.newInstance();
        childFragmentManager.beginTransaction()
                .add(R.id.mapContainer, supportMapFragment)
                .commit();

        supportMapFragment.getMapAsync(this);
    }

    override fun initEventHandlers(view: View?) {

    }

    override fun onMapReady(map: GoogleMap?) {

        if (map == null) {
            throw IllegalArgumentException("Map is not initialized unfortunately")
        }

        this.googleMap = map
        setUpMapSettings()

        // Notify the caller that the map is ready for use
        onMapInitialized();
    }

    /**
     * Setup the basic map settings
     */
    open protected fun setUpMapSettings() {
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        // Set ui settings
        val settings = googleMap.uiSettings
        settings.isMapToolbarEnabled = true // Enable marker click navigation
        // Enable additional gestures
        settings.isScrollGesturesEnabled = true
        settings.isTiltGesturesEnabled = true
        settings.isRotateGesturesEnabled = true
        settings.isZoomGesturesEnabled = true
        settings.isZoomControlsEnabled = true

        enableLocationSettings()
    }

    protected abstract fun onMapInitialized()

    protected fun enableLocationSettings() {

        if (!context.isPermissionsGranted(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            mapPermissionBtn.visibility = View.VISIBLE
            return
        }

        mapPermissionBtn.visibility = View.GONE

        googleMap.isMyLocationEnabled = true

        val settings = googleMap.uiSettings
        settings.isMyLocationButtonEnabled = true
    }
}