package com.dejvidleka.moviehub.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.local.models.Genre
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.moviehub.databinding.ItemCategoriesBinding
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import com.google.android.material.carousel.CarouselLayoutManager
import kotlinx.coroutines.launch

class GenreAdapter(
    private val mainViewModel: MainViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val onMovieClick: (movieResult: MovieResult, view: View) -> Unit, // Regular item clicks
    private val onViewMoreClick: (genre: Genre, view: View) -> Unit,
) : ListAdapter<Genre, GenreAdapter.GenreViewHolder>(GenreDiffUtil()) {
    inner class GenreViewHolder(val binding: ItemCategoriesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Genre) {
            binding.genre = genre
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoriesBinding.inflate(layoutInflater, parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = getItem(position)
        holder.bind(genre)
        val moviesAdapter = MovieListByGenreAdapter(genre = genre) { movieResult, view ->
            if (movieResult.isViewMore) {
                onViewMoreClick(genre, view) // Handle "View More" clicks
            } else {
                onMovieClick(movieResult, view) // Handle regular item clicks
            }
        }
        holder.binding.moviesRv.adapter = moviesAdapter
        holder.binding.moviesRv.layoutManager = CarouselLayoutManager()
        mainViewModel.setGenre(genre.id.toString())
        val moviesFlow = mainViewModel.moviesForGenre(genre.id.toString())

        lifecycleOwner.lifecycleScope.launch {
            moviesFlow.collect { movieResultsList ->
                when (movieResultsList) {
                    is Result.Loading -> {
                    }
                    is Result.Success -> {
                        val sortedMovies = movieResultsList.data.sortedBy { it.id }.toMutableList()
                        if (sortedMovies.isNotEmpty()) {
                            val lastMovie = sortedMovies.last().copy(isViewMore = true)
                            sortedMovies[sortedMovies.size - 1] = lastMovie
                        }
                        moviesAdapter.submitList(sortedMovies)
                    }
                    is Result.Error -> {
                    }
                }
            }
        }
    }

    private class GenreDiffUtil : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return newItem.name == oldItem.name
        }
        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return newItem == oldItem
        }
    }
}