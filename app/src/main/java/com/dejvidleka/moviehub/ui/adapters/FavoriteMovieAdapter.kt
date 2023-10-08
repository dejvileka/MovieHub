package com.dejvidleka.moviehub.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.local.models.MovieEntity
import com.dejvidleka.data.local.models.SimilarMoviesResult
import com.dejvidleka.moviehub.databinding.ItemFavoriteMovieBinding
import com.dejvidleka.moviehub.databinding.ItemSimilarMoviesBinding

class FavoriteMovieAdapter : ListAdapter<MovieEntity, FavoriteMovieAdapter.FavoriteMoviesViewHolder>(FavoriteMoviesDiffUtil()) {
    inner class FavoriteMoviesViewHolder(private val itemBinding: ItemFavoriteMovieBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(favorites: MovieEntity) {
            itemBinding.movie = favorites

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


