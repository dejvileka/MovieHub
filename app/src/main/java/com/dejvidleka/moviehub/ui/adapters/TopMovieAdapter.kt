package com.dejvidleka.moviehub.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.moviehub.databinding.ItemMovieByCategorieBinding
import com.dejvidleka.moviehub.databinding.ItemTopMoviesBinding
import com.dejvidleka.moviehub.databinding.ItemWhatToWhatchBinding
import com.dejvidleka.moviehub.utils.MovieClickListener


class TopMovieAdapter (
    private val onClick: MovieClickListener
):
    ListAdapter<MovieResult, TopMovieAdapter.TopMovieViewHolder>(TopMovieDiffUtil()) {
    inner class TopMovieViewHolder(private val itemBinding: ItemWhatToWhatchBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movies: MovieResult) {
            itemBinding.movie = movies
            itemBinding.root.setOnClickListener { onClick.onMovieClick(movies, it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMovieViewHolder {
        val binding = ItemWhatToWhatchBinding.inflate(LayoutInflater.from(parent.context))
        return TopMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopMovieViewHolder, position: Int) {
        val moviesResult = getItem(position)
        holder.bind(moviesResult)
    }
}

private class TopMovieDiffUtil : DiffUtil.ItemCallback<MovieResult>() {
    override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        return newItem == oldItem
    }

}