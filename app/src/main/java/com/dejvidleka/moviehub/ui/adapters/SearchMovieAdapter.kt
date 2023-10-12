package com.dejvidleka.moviehub.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.moviehub.databinding.ItemMovieByCategorieBinding

class SearchMovieAdapter : ListAdapter<MovieResult, SearchMovieAdapter.SearchMoviesViewHolder>(SearchResultDiffUtil()) {

    inner class SearchMoviesViewHolder(private val itemBinding: ItemMovieByCategorieBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movieResult: MovieResult) {
        itemBinding.movie=movieResult
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMoviesViewHolder {
        val binding = ItemMovieByCategorieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchMoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchMoviesViewHolder, position: Int) {
        val movieResult = getItem(position)
        holder.bind(movieResult)
    }
}

private class SearchResultDiffUtil : DiffUtil.ItemCallback<MovieResult>() {
    override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        return oldItem == newItem
    }
}