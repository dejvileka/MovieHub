package com.dejvidleka.moviehub

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dejvidleka.moviehub.databinding.FragmentMoreBinding
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import com.dejvidleka.moviehub.utils.AppPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoreFragment : Fragment() {

    private lateinit var binding: FragmentMoreBinding
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoreBinding.inflate(
            LayoutInflater.from(this.requireContext()),
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.regions.collect {result->
                when (result){
                    is Result.Success -> {
                        val regionsName = result.data.map { it.english_name + " " + it.iso_3166_1 }
                        Log.d("Regions", result.data.toString())
                        val adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_dropdown_item_1line,
                            regionsName
                        )
                        val locationAutocomplete = binding.locationAutocomplete
                            locationAutocomplete.setAdapter(adapter)
                            locationAutocomplete.threshold = 1
                        binding.savePreferencesButton.setOnClickListener {
                            val selectedText = binding.locationAutocomplete.text.toString()
                            val regions = regionsName.filter { it.startsWith(selectedText) }
                            if (regions.isNotEmpty()) {
                                val selectedRegionCode = regions.first().split(" ").last() // Extract ISO code
                                AppPreferences.saveRegionCode(requireContext(), selectedRegionCode)
                            } else {
                                // Handle the case where the region isn't found (e.g., show a message)
                            }
                        }
                    }


                    is Result.Loading -> {}
                    is Result.Error -> {}
                }
        }}



    }

}