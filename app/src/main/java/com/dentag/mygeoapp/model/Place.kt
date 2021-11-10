package com.dentag.mygeoapp.model

import com.google.android.gms.maps.model.LatLng

data class Place(
    val id: String,
    var name: String,
    val position: LatLng
)