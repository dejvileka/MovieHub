package com.dejvidleka.moviehub.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dejvidleka.data.local.models.Genre
import com.dejvidleka.data.local.models.MovieResult
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

    companion object {
        fun newInstance() = FavoritesFragment()
    }

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
        binding.favoritesRV.layoutManager = LinearLayoutManager(requireContext())
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
    }

    override fun onMovieClick(movieResult: MovieResult, view: View) {
        val directions =
            FavoritesFragmentDirections.actionFavoritesFragmentToMovieDetailFragment(movieResult)
        view.findNavController().navigate(directions)
    }

    override fun onViewMoreClick(genre: Genre, view: View) {
    }
}