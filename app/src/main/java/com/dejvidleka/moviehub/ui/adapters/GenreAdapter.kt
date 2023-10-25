package com.dejvidleka.moviehub.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.local.models.Genre
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.moviehub.databinding.ItemCategoriesBinding
import com.dejvidleka.moviehub.ui.home.FirstFragmentDirections
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import com.dejvidleka.moviehub.utils.MovieClickListener

class GenreAdapter(
    private val isTv: Boolean = false,
    private val mainViewModel: MainViewModel,
    private val lifecycleOwner: LifecycleOwner,
) : MovieClickListener, ListAdapter<Genre, GenreAdapter.GenreViewHolder>(GenreDiffUtil()) {
    inner class GenreViewHolder(val binding: ItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
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
        /*        val moviesAdapter = MovieListByGenreAdapter(genre, onClick = this,hasViewMore = true)
                holder.binding.moviesRv.adapter = moviesAdapter
                holder.binding.moviesRv.layoutManager = CarouselLayoutManager()
                val snapHelper = CarouselSnapHelper(false)
                snapHelper.attachToRecyclerView(holder.binding.moviesRv)

                lifecycleOwner.lifecycleScope.launch {
                    mainViewModel.m.collect { movieResultsList ->
                        when (movieResultsList) {
                            is Result.Loading -> {}
                            is Result.Success -> {
                                val sortedMovies = movieResultsList.data.sortedBy { it.id }.toMutableList()
                                if (sortedMovies.isNotEmpty()) {
                                    val lastMovie = sortedMovies.last().copy(isViewMore = true)
                                    sortedMovies[sortedMovies.size - 1] = lastMovie
                                }
                                moviesAdapter.submitList(sortedMovies)
                                Log.d("shows", "${sortedMovies}")
                            }
                            is Result.Error -> {}
                        }
                    }
                }*/
    }

    private class GenreDiffUtil : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return newItem.name == oldItem.name
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return newItem == oldItem
        }
    }

    override fun onMovieClick(movieResult: MovieResult, view: View) {
        val directions = FirstFragmentDirections.actionHomeToMovieDetail(movieResult)
        view.findNavController().navigate(directions)
    }

    override fun onViewMoreClick(genre: Genre, view: View) {
        val directions = FirstFragmentDirections.actionFirstFragmentToMoreMoviesPerGenre(genre)
        view.findNavController().navigate(directions)
    }
}