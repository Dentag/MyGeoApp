package com.dentag.mygeoapp.repository

import com.dentag.mygeoapp.model.Place
import com.google.android.gms.maps.model.LatLng

object FakePlaceRepository : PlaceRepository {
    private var placeList: MutableList<Place> = mutableListOf()

    override fun getPlaces(): List<Place> {
        return placeList.toList()
    }

    override fun addPlace(place: Place) {
        placeList.add(place)
    }

    override fun removePlace(position: LatLng) {
        placeList.removeIf { it.position == position }
    }

    override fun renamePlace(placeId: String, newName: String) {
        val index = placeList.indexOfFirst { it.id == placeId }
        placeList[index] = placeList[index].copy(name = newName)
    }
}