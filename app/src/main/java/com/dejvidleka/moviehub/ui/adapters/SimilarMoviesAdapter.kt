package com.dejvidleka.moviehub.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.local.models.SimilarMoviesResult
import com.dejvidleka.moviehub.databinding.ItemSimilarMoviesBinding

class SimilarMoviesAdapter : ListAdapter<SimilarMoviesResult, SimilarMoviesAdapter.SimilarMoviesViewHolder>(SimilarMoviesDiffUtil()) {
    inner class SimilarMoviesViewHolder(private val itemBinding: ItemSimilarMoviesBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(similarMovies: SimilarMoviesResult) {
            itemBinding.movie = similarMovies
        }

        /* private fun navigateToDetails(movieResult: MovieResult, view: View) {
            val directions = FirstFragmentDirections.actionHomeToMovieDetail(movieResult)
            view.findNavController().navigate(directions)
        }

    }

    private fun navigateToMoreMovies(genre: Genre, view: View) {
        val directions = FirstFragmentDirections.actionFirstFragmentToMoreMoviesPerGenre(genre)
        view.findNavController().navigate(directions)*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMoviesViewHolder {
        val binding = ItemSimilarMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimilarMoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimilarMoviesViewHolder, position: Int) {
        val moviesResult = getItem(position)
        holder.bind(moviesResult)
    }
}

private class SimilarMoviesDiffUtil : DiffUtil.ItemCallback<SimilarMoviesResult>() {
    override fun areItemsTheSame(oldItem: SimilarMoviesResult, newItem: SimilarMoviesResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SimilarMoviesResult, newItem: SimilarMoviesResult): Boolean {
        return oldItem == newItem
    }

}


