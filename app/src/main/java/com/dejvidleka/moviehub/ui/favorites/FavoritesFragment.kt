package com.dejvidleka.moviehub.ui.favorites

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dejvidleka.data.local.models.Genre
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.moviehub.R
import com.dejvidleka.moviehub.databinding.FragmentFavoritesBinding
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.ui.adapters.FavoriteMovieAdapter
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import com.dejvidleka.moviehub.utils.MovieClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(), MovieClickListener {
    private val viewModel: FavoritesViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FavoriteMovieAdapter(mainViewModel, onClick = this)
        binding.favoritesRV.adapter = adapter
        binding.favoritesRV.layoutManager = GridLayoutManager(requireContext(),20)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getAllFavoriteMovies().collect { result ->
                when (result) {
                    is Result.Success -> {
                        adapter.submitList(result.data)
                    }

                    is Result.Loading -> {
                    }

                    is Result.Error -> {
                    }
                }
            }
        }
        val spinnerYear = binding.spinnerYear
        setupYearSpinner(this.requireContext(), spinnerYear)

    }


    override fun onMovieClick(movieResult: MovieResult, view: View) {
        val directions =
            FavoritesFragmentDirections.actionFavoritesFragmentToMovieDetailFragment(movieResult)
        view.findNavController().navigate(directions)
    }


    private fun setupYearSpinner(context: Context, spinner: Spinner) {
        ArrayAdapter.createFromResource(
            context,
            R.array.years_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        val seekBarAgeRating = binding.seekbarAgeRating
        val textViewSelectedAge = binding.textviewSelectedAge

        seekBarAgeRating.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewSelectedAge.text = "$progress years"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    override fun onViewMoreClick(genre: Genre, view: View) {
    }
}