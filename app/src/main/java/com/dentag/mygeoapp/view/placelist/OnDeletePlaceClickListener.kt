package com.dentag.mygeoapp.view.placelist

import com.google.android.gms.maps.model.LatLng

fun interface OnDeletePlaceClickListener {
    fun onDeletePlaceClick(placePosition: LatLng)
}