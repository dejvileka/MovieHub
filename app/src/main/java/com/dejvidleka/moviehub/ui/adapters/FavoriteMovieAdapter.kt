package com.dejvidleka.moviehub.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.local.models.MovieEntity
import com.dejvidleka.data.local.models.toMovieResult
import com.dejvidleka.moviehub.databinding.ItemFavoriteMovieBinding
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import com.dejvidleka.moviehub.utils.MovieClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteMovieAdapter(
    val mainViewModel: MainViewModel,
    private val onClick: MovieClickListener
) :
    ListAdapter<MovieEntity, FavoriteMovieAdapter.FavoriteMoviesViewHolder>(FavoriteMoviesDiffUtil()) {
    inner class FavoriteMoviesViewHolder(private val itemBinding: ItemFavoriteMovieBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(favorites: MovieEntity) {
            itemBinding.movie = favorites
            val movieResult = favorites.toMovieResult()
            itemBinding.removeItem.setOnClickListener {
                mainViewModel.viewModelScope.launch(Dispatchers.IO) {
                    try {
                        mainViewModel.removeFavorite(favorites)
                        launch(Dispatchers.IO) {
                        }
                    } catch (e: Exception) {
                        launch(Dispatchers.IO) {
                        }
                    }
                }
            }
            itemBinding.setClickListener{ onClick.onMovieClick(movieResult = movieResult,it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMoviesViewHolder {
        val binding =
            ItemFavoriteMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteMoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteMoviesViewHolder, position: Int) {
        val moviesResult = getItem(position)
        holder.bind(moviesResult)
    }
}

private class FavoriteMoviesDiffUtil : DiffUtil.ItemCallback<MovieEntity>() {
    override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return oldItem == newItem
    }

}


