package com.dejvidleka.moviehub.ui.home

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.dejvidleka.data.network.models.SimilarMovies
import com.dejvidleka.moviehub.R
import com.dejvidleka.moviehub.databinding.FragmentMovieDetailBinding
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.ui.adapters.MovieCastAdapter
import com.dejvidleka.moviehub.ui.adapters.SimilarMoviesAdapter
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import com.dejvidleka.moviehub.utils.VideoHandler
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var similarMoviesAdapter: SimilarMoviesAdapter
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var castAdapter: MovieCastAdapter
    private var originalBackgroundColor: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        originalBackgroundColor = (binding.scrollable.background as? GradientDrawable)?.color?.defaultColor
        hideBottomNavigation()
        setupUIComponents()
        loadMovieDetails()
        loadMovieCast()
        loadMovieTrailer()
        showSimilarMovies()
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
            detailImage.loadImageAndExtractColor(args.movieResult.backdrop_path ?: args.movieResult.poster_path)
        }
    }

    private fun ImageView.loadImageAndExtractColor(path: String?) {
        val baseURL = "https://image.tmdb.org/t/p/w500"
        val imageUrl = baseURL + path
        Glide.with(this@MovieDetailFragment)
            .asBitmap() // Important: get a bitmap rather than a drawable
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    this@loadImageAndExtractColor.setImageBitmap(resource)

                    Palette.from(resource).generate { palette ->
                        val vibrantColor = palette?.vibrantSwatch?.rgb
                        val dominantColor= palette?.dominantSwatch?.rgb
                        val mainColor= palette?.lightVibrantSwatch?.rgb
                        if (vibrantColor != null) {
                            val background = binding.scrollable.background as GradientDrawable
                            background.setColor(vibrantColor)
                        }
                    }
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }


    private fun loadMovieDetails() {
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

    override fun onDestroyView() {
        super.onDestroyView()
        showBottomNavigation()
        originalBackgroundColor?.let {
            val background = binding.scrollable.background as? GradientDrawable
            background?.setColor(it)
        }
        _binding = null
    }

    private fun showSimilarMovies() {
        val args = MovieDetailFragmentArgs.fromBundle(requireArguments())
        similarMoviesAdapter = SimilarMoviesAdapter()
        binding.similarMovieRv.layoutManager= GridLayoutManager(this.requireContext(), 3)
        binding.similarMovieRv.adapter = similarMoviesAdapter
        lifecycleScope.launch {
            mainViewModel.getSimilarMovies(args.movieResult.id).collect { result ->
                when (result) {
                    is Result.Loading -> showToast("Wait")
                    is Result.Success -> {
                        Log.d("My List","${result.data}")
                        similarMoviesAdapter.submitList(result.data)
                    }

                    is Result.Error -> showToast("Shame")
                }

            }
        }
    }

}


