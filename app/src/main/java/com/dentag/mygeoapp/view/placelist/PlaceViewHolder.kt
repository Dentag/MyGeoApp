package com.dentag.mygeoapp.view.placelist

import androidx.recyclerview.widget.RecyclerView
import com.dentag.mygeoapp.databinding.ItemPlaceBinding
import com.dentag.mygeoapp.model.Place

class PlaceViewHolder(
    private val binding: ItemPlaceBinding,
    private val onDeletePlaceClickListener: OnDeletePlaceClickListener,
    private val onChangePlaceNameClickListener: OnChangePlaceNameClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(place: Place) {
        with(binding) {
            val placePositionText =
                "${"%.2f".format(place.position.latitude)}/${"%.2f".format(place.position.longitude)}"
            placePosition.text = placePositionText
            placeName.text = place.name

            itemView.setOnLongClickListener {
                onChangePlaceNameClickListener.onChangePlaceNamePlaceClick(place.id, place.name)
                true
            }
            btnDeletePlace.setOnClickListener {
                onDeletePlaceClickListener.onDeletePlaceClick(place.position)
            }
        }
    }
}