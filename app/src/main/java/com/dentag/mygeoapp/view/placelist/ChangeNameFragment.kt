package com.dentag.mygeoapp.view.placelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.dentag.mygeoapp.databinding.FragmentChangeNameBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChangeNameFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentChangeNameBinding? = null
    private val binding get() = _binding!!

    private lateinit var requestKey: String
    private lateinit var resultKey: String
    private lateinit var oldName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentChangeNameBinding.inflate(inflater, container, false).also { _binding = it }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupArguments()
        setupView()
    }

    private fun setupView() {
        binding.placeNameEditText.setText(oldName)
        binding.okBtn.setOnClickListener {
            val newName = binding.placeNameEditText.text
            if (!newName.isNullOrBlank()) {
                parentFragmentManager.setFragmentResult(
                    requestKey,
                    bundleOf(resultKey to newName.toString())
                )
                dismiss()
            }
        }
    }

    private fun setupArguments() {
        arguments?.let {
            requestKey = it.getString(REQUEST_KEY_EXTRA) ?: ""
            resultKey = it.getString(RESULT_KEY_EXTRA) ?: ""
            oldName = it.getString(OLD_NAME_EXTRA) ?: ""
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val REQUEST_KEY_EXTRA =
            "com.dentag.mygeoapp.view.placelist.ChangeNameFragment.REQUEST_KEY_EXTRA"
        private const val RESULT_KEY_EXTRA =
            "com.dentag.mygeoapp.view.placelist.ChangeNameFragment.RESULT_KEY_EXTRA"
        private const val OLD_NAME_EXTRA =
            "com.dentag.mygeoapp.view.placelist.ChangeNameFragment.OLD_NAME_EXTRA"

        fun newInstance(
            requestKey: String,
            resultKey: String,
            oldName: String
        ): ChangeNameFragment {
            return ChangeNameFragment().apply {
                arguments = bundleOf(
                    REQUEST_KEY_EXTRA to requestKey,
                    RESULT_KEY_EXTRA to resultKey,
                    OLD_NAME_EXTRA to oldName
                )
            }
        }
    }
}