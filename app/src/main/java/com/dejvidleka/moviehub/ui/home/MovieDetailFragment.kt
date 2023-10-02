package com.dejvidleka.moviehub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dejvidleka.data.network.models.Cast
import com.dejvidleka.moviehub.R
import com.dejvidleka.moviehub.databinding.FragmentMovieDetailBinding
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.ui.adapters.MovieCastAdapter
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import com.dejvidleka.moviehub.utils.VideoHandler
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var castAdapter: MovieCastAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNavigation()
        setupMovieDetails()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        showBottomNavigation()
        _binding = null
    }

    private fun hideBottomNavigation() {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.GONE
    }

    private fun showBottomNavigation() {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.VISIBLE
    }

    private fun setupMovieDetails() {
        val args = MovieDetailFragmentArgs.fromBundle(requireArguments())
        val movieResult = args.movieResult

        binding.lifecycleOwner = viewLifecycleOwner
        binding.movieTitle.text = movieResult.title
        binding.movieDescription.text = movieResult.overview
        val castAdapter = MovieCastAdapter()
        binding.castRv.adapter= castAdapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.castRv.layoutManager = layoutManager
        mainViewModel.setMovieCast(args.movieResult.id)
        if (movieResult.backdrop_path == null) {
            loadImage(movieResult.poster_path)
        } else {
            loadImage(movieResult.backdrop_path)
        }
        val castFlow = mainViewModel.castForMovie(args.movieResult.id)

        viewLifecycleOwner.lifecycleScope.launch {
           castFlow.collect { cast ->
                when (cast) {
                    is Result.Loading -> {
                        Toast.makeText(requireContext(), "Wait", Toast.LENGTH_SHORT).show()
                    }

                    is Result.Success -> {
                        castAdapter.submitList(cast.data)
                        Toast.makeText(requireContext(), "yai", Toast.LENGTH_SHORT).show()

                    }

                    is Result.Error -> {
                        Toast.makeText(requireContext(), "Shame", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        mainViewModel.fetchTrailerForMovie(movieResult.id)
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.trailer.collect { key ->
                key?.let {
                    playTrailer(it)
                }
            }
        }
    }

    fun playTrailer(id:String) {
        val videoKey = id
        val videoUrl = "https://www.youtube.com/embed/$videoKey?autoplay=1"
        val webSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.allowFileAccess = true
        webSettings.allowContentAccess = true
        webSettings.domStorageEnabled = true
        webSettings.mediaPlaybackRequiresUserGesture = false
        webSettings.javaScriptEnabled = true

        binding.webView.loadUrl(videoUrl)
        binding.webView.webChromeClient = VideoHandler(
            nonVideoLayout = binding.coordinatorLayout,  // Assuming your ConstraintLayout id is constraintLayout
            videoLayout = binding.videoLayout,
            webView = binding.webView
        )
    }



    private fun loadImage(path: String?) {
        val baseURL = "https://image.tmdb.org/t/p/w500"
        val imageUrl = baseURL + path
        Glide.with(this)
            .load(imageUrl)
            .into(binding.detailImage)
    }
}

