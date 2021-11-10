package com.dentag.mygeoapp.view.placelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dentag.mygeoapp.databinding.ItemPlaceBinding
import com.dentag.mygeoapp.model.Place

class PlaceAdapter(
    private val onDeletePlaceClickListener: OnDeletePlaceClickListener,
    private val onChangePlaceNameClickListener: OnChangePlaceNameClickListener
) : ListAdapter<Place, PlaceViewHolder>(placeDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaceBinding.inflate(inflater, parent, false)
        return PlaceViewHolder(binding, onDeletePlaceClickListener, onChangePlaceNameClickListener)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val placeDiffUtil = object : DiffUtil.ItemCallback<Place>() {
            override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
                return oldItem == newItem
            }
        }
    }
}