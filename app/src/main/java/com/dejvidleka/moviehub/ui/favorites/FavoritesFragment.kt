package com.dejvidleka.moviehub.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dejvidleka.moviehub.databinding.FragmentFavoritesBinding
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.ui.adapters.FavoriteMovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private val viewModel: FavoritesViewModel by viewModels()
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
        val adapter = FavoriteMovieAdapter()
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
}