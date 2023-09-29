package com.dejvidleka.moviehub.ui.whattowatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.dejvidleka.moviehub.R
import com.dejvidleka.moviehub.databinding.FragmentWhatToWatchBinding
import com.dejvidleka.moviehub.databinding.ItemPosterBinding
import com.dejvidleka.moviehub.ui.adapters.ViewPagerAdapter
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import com.dejvidleka.moviehub.utils.bindImage
import dagger.hilt.android.AndroidEntryPoint
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
            val nextItem = (viewPager.currentItem + 1) % (viewPager.adapter?.itemCount ?: 1)
            viewPager.setCurrentItem(nextItem, true)
            handler.postDelayed(this, 3000)
        }
    }

    companion object {
        fun newInstance() = WhatToWatchFragment()
    }

    private lateinit var viewModel: WhatToWatchViewModel

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
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.fetchTopRatedMovies()
            mainViewModel.topMovie.collect { topMovie ->
                Log.d("List", "$topMovie");
                adapter.submitList(topMovie)
            }

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(update)  // Remove the callback when the view is destroyed.
    }

}