package com.dejvidleka.moviehub.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.moviehub.databinding.ItemSimilarMoviesBinding
import com.dejvidleka.moviehub.utils.MovieClickListener

class SimilarMoviesAdapter
    (private val onClick: MovieClickListener):
    ListAdapter<MovieResult, SimilarMoviesAdapter.SimilarMoviesViewHolder>(
        SimilarMoviesDiffUtil()
    ) {
    inner class SimilarMoviesViewHolder(private val itemBinding: ItemSimilarMoviesBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(similarMovies: MovieResult) {
            itemBinding.movie = similarMovies
            itemBinding.setClickListener {
                onClick.onMovieClick(movieResult = similarMovies,it)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMoviesViewHolder {
        val binding =
            ItemSimilarMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimilarMoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimilarMoviesViewHolder, position: Int) {
        val moviesResult = getItem(position)
        holder.bind(moviesResult)
    }
}

private class SimilarMoviesDiffUtil : DiffUtil.ItemCallback<MovieResult>() {
    override fun areItemsTheSame(
        oldItem: MovieResult,
        newItem: MovieResult
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MovieResult,
        newItem: MovieResult
    ): Boolean {
        return oldItem == newItem
    }

}


