package com.dejvidleka.moviehub.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.network.models.Genre
import com.dejvidleka.moviehub.databinding.ItemCategoriesBinding
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import com.google.android.material.carousel.CarouselLayoutManager

class GenreAdapter(
    private val mainViewModel: MainViewModel,
    private val lifecycleOwner: LifecycleOwner,
) : ListAdapter<Genre, GenreAdapter.GenreViewHolder>(GenreDiffUtil()) {
    inner class GenreViewHolder(val binding: ItemCategoriesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: com.dejvidleka.data.network.models.Genre) {
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

        val moviesAdapter = MovieListByGenreAdapter(genre)
        holder.binding.moviesRv.adapter = moviesAdapter
        holder.binding.moviesRv.layoutManager = CarouselLayoutManager()

        // Fetch the movies for this genre.
        mainViewModel.fetchMoviesByGenre(genre.id)


//        lifecycleOwner.lifecycleScope.launchWhenStarted {
//            mainViewModel.genres.collect { map ->
//                when(map){
//                    is Result.Success<*> ->
//                }
//
//                val movieResults = (map[genre.id] ?: emptyList()).toMutableList()
//                if (movieResults.isNotEmpty()) {
//                    val lastItem = movieResults.last().copy(isViewMore = true)
//                    movieResults[movieResults.size - 1] = lastItem
//                    moviesAdapter.submitList(movieResults)
//                }
//            }
//        }
    }


    private class GenreDiffUtil : DiffUtil.ItemCallback<com.dejvidleka.data.network.models.Genre>() {
        override fun areItemsTheSame(oldItem: com.dejvidleka.data.network.models.Genre, newItem: com.dejvidleka.data.network.models.Genre): Boolean {
            return newItem.name == oldItem.name
        }

        override fun areContentsTheSame(oldItem: com.dejvidleka.data.network.models.Genre, newItem: com.dejvidleka.data.network.models.Genre): Boolean {
            return newItem == oldItem
        }


    }
}