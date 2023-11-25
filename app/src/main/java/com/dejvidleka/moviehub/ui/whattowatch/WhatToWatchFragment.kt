package com.dejvidleka.moviehub.ui.whattowatch

import android.graphics.drawable.GradientDrawable.Orientation
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.local.models.Genre
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.moviehub.R
import com.dejvidleka.moviehub.databinding.FragmentWhatToWatchBinding
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.ui.adapters.TopMovieAdapter
import com.dejvidleka.moviehub.ui.adapters.TrendingCarousel
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import com.dejvidleka.moviehub.utils.MovieClickListener
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import com.google.android.material.carousel.MultiBrowseCarouselStrategy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WhatToWatchFragment : Fragment(), MovieClickListener {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentWhatToWatchBinding
    private lateinit var topMovieAdapter: TopMovieAdapter
    private lateinit var trendingMovieAdapter: TrendingCarousel
    private val handler = Handler(Looper.getMainLooper())
    private val update = object : Runnable {
        override fun run() {

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWhatToWatchBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chipCategories.setOnCheckedChangeListener { group, checkedId ->
            val category = when (checkedId) {
                R.id.chip_2_movies -> "movie"
                R.id.chip_3_shows -> "tv"
                else -> return@setOnCheckedChangeListener
            }
            mainViewModel.updateCategory(category)
        }

       binding.chipCategoriesTopGenres.setOnCheckedChangeListener { group, checkedId ->
            val section = when (checkedId) {
                R.id.chip_2_topRated -> "top_rated"
                R.id.chip_3_popular -> "popular"
                R.id.chip_3_now_playing -> "now_playing"
                else -> return@setOnCheckedChangeListener
            }
            mainViewModel.updateSection(section)
        }
        populationTopMovies()
        populateCard()

    }
    private fun populationTopMovies(){
        topMovieAdapter= TopMovieAdapter(this)
        binding.topRatedRv.adapter=topMovieAdapter
        binding.topRatedRv.layoutManager= GridLayoutManager(context,2)
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.topRatedMovies.collect { result ->
                when (result) {
                    is Result.Success -> {
                        Log.d("top list", result.data.toString())
                        topMovieAdapter.submitList(result.data)
                    }
                    is Result.Error -> {
                        Toast.makeText(requireContext(), "Shame", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Loading -> {
                    }
                }
            }
        }
    }
    private fun populateCard(){
        trendingMovieAdapter= TrendingCarousel(this)
        binding.trendingCarosel.adapter=trendingMovieAdapter
        binding.trendingCarosel.layoutManager= CarouselLayoutManager()
        val snapHelper = CarouselSnapHelper(false)
        snapHelper.attachToRecyclerView(binding.trendingCarosel)
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.getTrending().collect { result ->
                when (result) {
                    is Result.Success -> {
                        Log.d("trending", result.data.toString())
                        trendingMovieAdapter.submitList(result.data)
                        }
                    is Result.Error -> {
                        Toast.makeText(requireContext(), "Shame", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Loading -> {
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(update)
    }

    override fun onMovieClick(movieResult: MovieResult, view: View) {
        val navigation =
            WhatToWatchFragmentDirections.actionWhatToWatchFragmentToMovieDetailFragment(movieResult)
        view.findNavController().navigate(navigation)
    }

    override fun onViewMoreClick(genre: Genre, view: View) {
    }


}