package com.dentag.mygeoapp.view.placelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dentag.mygeoapp.databinding.FragmentPointListBinding
import com.dentag.mygeoapp.model.Place

class PlaceListFragment : Fragment() {
    private var _binding: FragmentPointListBinding? = null
    private val binding get() = _binding!!
    private var placeAdapter: PlaceAdapter? = null
    private lateinit var placesViewModel: PlaceListViewModel

    private val onDeletePlaceClickListener = OnDeletePlaceClickListener { position ->
        placesViewModel.removePlace(position)
    }

    private val onChangePlaceNameClickListener =
        OnChangePlaceNameClickListener { placeId, oldName ->
            parentFragmentManager.setFragmentResultListener(
                REQUEST_KEY,
                viewLifecycleOwner
            ) { _, bundle ->
                val newName = bundle.getString(RESULT_KEY)
                newName?.let {
                    placesViewModel.renamePlace(placeId, newName)
                }
            }
            ChangeNameFragment.newInstance(REQUEST_KEY, RESULT_KEY, oldName)
                .show(parentFragmentManager, null)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentPointListBinding.inflate(layoutInflater, container, false)
        .also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRecyclerView()
    }

    private fun initViewModel() {
        placesViewModel = ViewModelProvider(this).get(PlaceListViewModel::class.java)
    }

    private fun initRecyclerView() {
        placeAdapter = PlaceAdapter(onDeletePlaceClickListener, onChangePlaceNameClickListener)
        binding.pointRecyclerView.apply {
            adapter = placeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        placesViewModel.placesLiveData.observe(viewLifecycleOwner) { places ->
            submitList(places)
        }
    }

    private fun submitList(places: List<Place>) {
        placeAdapter?.submitList(places)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        placeAdapter = null
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val REQUEST_KEY =
            "request"
        private const val RESULT_KEY =
            "result"
    }
}