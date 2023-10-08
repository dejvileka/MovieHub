package com.dejvidleka.moviehub.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.local.models.MovieEntity
import com.dejvidleka.data.local.models.SimilarMoviesResult
import com.dejvidleka.data.local.models.toEntity
import com.dejvidleka.moviehub.databinding.ItemFavoriteMovieBinding
import com.dejvidleka.moviehub.databinding.ItemSimilarMoviesBinding
import com.dejvidleka.moviehub.ui.favorites.FavoritesViewModel
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class FavoriteMovieAdapter(val mainViewModel: MainViewModel) :
    ListAdapter<MovieEntity, FavoriteMovieAdapter.FavoriteMoviesViewHolder>(FavoriteMoviesDiffUtil()) {
    inner class FavoriteMoviesViewHolder(private val itemBinding: ItemFavoriteMovieBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(favorites: MovieEntity) {
            itemBinding.movie = favorites
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMoviesViewHolder {
        val binding = ItemFavoriteMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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


