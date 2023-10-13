package com.dejvidleka.moviehub.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.data.repo.MoviesRepository
import com.dejvidleka.moviehub.R
import com.dejvidleka.moviehub.databinding.FragmentMoreMoviesPerGenreBinding
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.domain.toResult
import com.dejvidleka.moviehub.ui.adapters.MoreMoviesByGenreAdapter
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoreMoviesPerGenre : Fragment() {

    private var _binding: FragmentMoreMoviesPerGenreBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: MoreMoviesByGenreAdapter
    private var currentPage = 1
    private val maxPages = 10

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMoreMoviesPerGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNavigation()

        setupRecyclerView()
        loadMovies()
    }

    private fun setupRecyclerView() {
        adapter = MoreMoviesByGenreAdapter()
        binding.allMoviesRv.adapter = adapter
        binding.allMoviesRv.layoutManager = LinearLayoutManager(context, GridLayoutManager.VERTICAL, false)
        binding.allMoviesRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && currentPage <= maxPages) {
                    loadMovies()
                }
            }
        })
    }

    private fun loadMovies() {
        val args = MoreMoviesPerGenreArgs.fromBundle(requireArguments())
        mainViewModel.setGenre(args.genre.id.toString())
        binding.genreTitle.text = args.genre.name

        lifecycleScope.launch {
            mainViewModel.moviesForGenre(args.genre.id.toString(), page = currentPage).collect { movieResultsList ->
                when (movieResultsList) {
                    is Result.Loading -> { /* Show a loading indicator if needed */
                    }

                    is Result.Success -> handleSuccess(movieResultsList.data)
                    is Result.Error -> showToast("Error loading movies")
                }
            }
        }
    }

    private fun handleSuccess(movies: List<MovieResult>) {
        val sortedMovies = movies.sortedBy { it.id }.toMutableList()
        if (currentPage == maxPages && sortedMovies.isNotEmpty()) {
            sortedMovies.last().isViewMore = true
        }
        adapter.submitList(adapter.currentList + sortedMovies)
        currentPage++
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun hideBottomNavigation() {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView?.visibility = View.GONE
    }

    private fun showBottomNavigation() {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView?.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        showBottomNavigation()
        _binding = null
    }
}
