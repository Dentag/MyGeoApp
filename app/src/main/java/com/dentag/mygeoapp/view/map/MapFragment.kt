package com.dentag.mygeoapp.view.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.dentag.mygeoapp.model.Place
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapFragment : SupportMapFragment() {
    private lateinit var mapViewModel: MapViewModel

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                updateLocationUI()
            } else {
                Toast.makeText(requireContext(), "Location permission needed", Toast.LENGTH_LONG)
                    .show()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        getMapAsync { googleMap ->
            getAllMarkers(googleMap)
            googleMap.setOnMapLongClickListener { position ->
                addMarker(googleMap, position)
            }
            googleMap.setOnMarkerClickListener { marker ->
                removeMarker(marker)
                true
            }
        }
        updateLocationUI()
    }

    override fun onResume() {
        super.onResume()
        getMapAsync { googleMap ->
            getAllMarkers(googleMap)
        }
    }

    private fun initViewModel() {
        mapViewModel = ViewModelProvider(this).get(MapViewModel::class.java)
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            updateLocationUI()
        } else {
            requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationUI() {
        getMapAsync { map ->
            if (ContextCompat.checkSelfPermission(
                    requireContext().applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                map.isMyLocationEnabled = true
                map.uiSettings.isMyLocationButtonEnabled = true
            } else {
                map.isMyLocationEnabled = false
                map.uiSettings.isMyLocationButtonEnabled = false
                getLocationPermission()
            }
        }
    }

    private fun removeMarker(marker: Marker) {
        marker.remove()
        mapViewModel.removePlace(marker.position)
    }

    private fun addMarker(googleMap: GoogleMap, position: LatLng) {
        googleMap.addMarker(
            MarkerOptions()
                .position(position)
        )
        addNewPlace(position)
    }

    private fun getAllMarkers(googleMap: GoogleMap) {
        googleMap.clear()
        mapViewModel.getPlaces()
        mapViewModel.placesLiveData.observe(viewLifecycleOwner) { placeList ->
            placeList.forEach { place ->
                googleMap.addMarker(
                    MarkerOptions()
                        .title(place.name)
                        .position(place.position)
                )
            }
        }
    }

    private fun addNewPlace(position: LatLng) {
        val place = Place(
            UUID.randomUUID().toString(),
            "",
            position
        )
        mapViewModel.addPlace(place)
    }
}