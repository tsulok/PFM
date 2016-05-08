package com.pinup.pfm.ui.core.view

import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.pinup.pfm.R

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
    protected fun setUpMapSettings() {
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap.isMyLocationEnabled = true

        // Set ui settings
        val settings = googleMap.uiSettings
        settings.isMapToolbarEnabled = true // Enable marker click navigation
        // Enable additional gestures
        settings.isScrollGesturesEnabled = true
        settings.isTiltGesturesEnabled = true
        settings.isRotateGesturesEnabled = true
        settings.isZoomGesturesEnabled = true
        settings.isZoomControlsEnabled = true
        settings.isMyLocationButtonEnabled = true
    }

    protected abstract fun onMapInitialized()
}