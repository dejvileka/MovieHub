package com.dejvidleka.moviehub.ui.whattowatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.dejvidleka.moviehub.R
import com.dejvidleka.moviehub.databinding.FragmentWhatToWatchBinding
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.ui.adapters.ViewPagerAdapter
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WhatToWatchFragment : Fragment() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var binding: FragmentWhatToWatchBinding
    private lateinit var viewPager: ViewPager2
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

        adapter = ViewPagerAdapter()
        viewPager = binding.bannerCarousel
        viewPager.adapter = adapter
        handler.postDelayed(update, 3000)
        binding.chipCategories.setOnCheckedChangeListener { group, checkedId ->
            val category = when (checkedId) {
                R.id.chip_2_movies -> "movie"
                R.id.chip_3_shows -> "tv"
                else -> return@setOnCheckedChangeListener
            }
            mainViewModel.updateCategory(category)
        }

        // Here, you collect the topRatedMovies StateFlow from your ViewModel
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.topRatedMovies.collect { result ->
                when (result) {
                    is Result.Success -> {
                        adapter.submitList(result.data)
                    }
                    is Result.Error -> {
                        Toast.makeText(requireContext(), "Shame", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Loading -> {
                        // handle loading state
                    }
                    null -> {
                        // handle the initial state or do nothing
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(update)
    }

}