package com.dentag.mygeoapp.view.placelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dentag.mygeoapp.model.Place
import com.dentag.mygeoapp.repository.FakePlaceRepository
import com.dentag.mygeoapp.repository.PlaceRepository
import com.google.android.gms.maps.model.LatLng

class PlaceListViewModel(
    private val placeRepository: PlaceRepository = FakePlaceRepository
) : ViewModel() {

    private val mLiveData: MutableLiveData<List<Place>> = MutableLiveData()
    val placesLiveData: LiveData<List<Place>> get() = mLiveData

    init {
        getPlaces()
    }

    fun renamePlace(placeId: String, newName: String) {
        placeRepository.renamePlace(placeId, newName)
        getPlaces()
    }

    fun removePlace(placePosition: LatLng) {
        placeRepository.removePlace(placePosition)
        getPlaces()
    }

    private fun getPlaces() {
        val places = placeRepository.getPlaces()
        mLiveData.postValue(places)
    }
}