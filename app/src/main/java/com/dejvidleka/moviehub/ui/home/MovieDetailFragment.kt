package com.dejvidleka.moviehub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
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
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var castAdapter: MovieCastAdapter

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
        setupUIComponents()
        loadMovieDetails()
        loadMovieCast()
        loadMovieTrailer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        showBottomNavigation()
        _binding = null
    }

    private fun hideBottomNavigation() {
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility = View.GONE
    }

    private fun showBottomNavigation() {
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility = View.VISIBLE
    }

    private fun setupUIComponents() {
        val args = MovieDetailFragmentArgs.fromBundle(requireArguments())
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            movieTitle.text = args.movieResult.title
            movieDescription.text = args.movieResult.overview
            castRv.adapter = MovieCastAdapter().also { castAdapter = it }
            castRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            detailImage.loadImage(args.movieResult.backdrop_path ?: args.movieResult.poster_path)
        }
    }

    private fun ImageView.loadImage(path: String?) {
        val baseURL = "https://image.tmdb.org/t/p/w500"
        val imageUrl = baseURL + path
        Glide.with(this@MovieDetailFragment)
            .load(imageUrl)
            .into(this)
    }

    private fun loadMovieDetails() {
        val args = MovieDetailFragmentArgs.fromBundle(requireArguments())
    }

    private fun loadMovieCast() {
        val args = MovieDetailFragmentArgs.fromBundle(requireArguments())
        val castFlow = mainViewModel.castForMovie(args.movieResult.id)

        viewLifecycleOwner.lifecycleScope.launch {
            castFlow.collect { result ->
                when (result) {
                    is Result.Loading -> showToast("Wait")
                    is Result.Success -> {
                        castAdapter.submitList(result.data)
                        showToast("yai")
                    }
                    is Result.Error -> showToast("Shame")
                }
            }
        }
    }

    private fun loadMovieTrailer() {
        val args = MovieDetailFragmentArgs.fromBundle(requireArguments())
        val trailerFlow = mainViewModel.trailerForMovie(args.movieResult.id)

        viewLifecycleOwner.lifecycleScope.launch {
            trailerFlow.collect { result ->
                when (result) {
                    is Result.Loading -> showToast("Wait")
                    is Result.Success -> playTrailer(result.data.key)
                    is Result.Error -> showToast("Shame")
                }
            }
        }
    }

    private fun playTrailer(trailerKey: String) {
        val videoUrl = "https://www.youtube.com/embed/$trailerKey?autoplay=1"
        binding.webView.apply {
            settings.apply {
                javaScriptEnabled = true
                allowFileAccess = true
                allowContentAccess = true
                domStorageEnabled = true
                mediaPlaybackRequiresUserGesture = false
            }
            loadUrl(videoUrl)
            webChromeClient = VideoHandler(
                nonVideoLayout = binding.coordinatorLayout,
                videoLayout = binding.videoLayout,
                webView = this
            )
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}


