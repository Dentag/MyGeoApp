package com.dentag.mygeoapp.repository

import com.dentag.mygeoapp.model.Place
import com.google.android.gms.maps.model.LatLng

interface PlaceRepository {
    fun getPlaces(): List<Place>
    fun addPlace(place: Place)
    fun removePlace(position: LatLng)
    fun renamePlace(placeId: String, newName: String)
}