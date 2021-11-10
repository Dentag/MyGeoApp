package com.dentag.mygeoapp.view.placelist

fun interface OnChangePlaceNameClickListener {
    fun onChangePlaceNamePlaceClick(placeId: String, oldName: String)
}