package com.dejvidleka.moviehub.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.dejvidleka.moviehub.R
import com.dejvidleka.moviehub.databinding.FragmentMoreMoviesPerGenreBinding
import com.dejvidleka.moviehub.ui.adapters.MoreMoviesByGenreAdapter
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
@AndroidEntryPoint
class MoreMoviesPerGenre : Fragment() {

    private var _binding: FragmentMoreMoviesPerGenreBinding? = null
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: MoreMoviesByGenreAdapter


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMoreMoviesPerGenreBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNavigation()
        adapter = MoreMoviesByGenreAdapter()

        val layoutManager = LinearLayoutManager(context, GridLayoutManager.VERTICAL, false)
        val args = MoreMoviesPerGenreArgs.fromBundle(requireArguments())
        binding.genreTitle.text = args.genre.name
        binding.allMoviesRv.adapter = adapter
        binding.allMoviesRv.layoutManager = layoutManager
        mainViewModel.fetchAllMoviesByGenre(args.genre.id)
        lifecycleScope.launch {
            mainViewModel.allMovies.collect { movieResultsList ->
                val movieResults = movieResultsList.toMutableList()
                if (movieResults.isNotEmpty()) {
                    val lastItem = movieResults.last().copy(isViewMore = true)
                    movieResults[movieResults.size - 1] = lastItem
                    adapter.submitList(movieResults)
                }
            }
        }

    }

    private fun hideBottomNavigation() {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.GONE
    }

    private fun showBottomNavigation() {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.VISIBLE
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        showBottomNavigation()
        _binding = null
    }
}
