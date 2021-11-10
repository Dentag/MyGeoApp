package com.dentag.mygeoapp.view.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dentag.mygeoapp.model.Place
import com.dentag.mygeoapp.repository.FakePlaceRepository
import com.dentag.mygeoapp.repository.PlaceRepository
import com.google.android.gms.maps.model.LatLng

class MapViewModel(
    private val placeRepository: PlaceRepository = FakePlaceRepository,
) : ViewModel() {

    private val mLiveData: MutableLiveData<List<Place>> = MutableLiveData()
    val placesLiveData: LiveData<List<Place>> get() = mLiveData

    fun removePlace(position: LatLng) {
        placeRepository.removePlace(position)
    }

    fun addPlace(place: Place) {
        placeRepository.addPlace(place)
    }

    fun getPlaces() {
        val places = placeRepository.getPlaces()
        mLiveData.postValue(places)
    }
}