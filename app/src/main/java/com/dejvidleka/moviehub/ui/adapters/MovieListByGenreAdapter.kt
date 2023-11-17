package com.dejvidleka.moviehub.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.local.models.Genre
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.moviehub.databinding.ItemMovieByCategorieBinding
import com.dejvidleka.moviehub.utils.MovieClickListener


class MovieListByGenreAdapter(
    private val genre: Genre?,
    private val onClick: MovieClickListener,
    private val hasViewMore: Boolean
) : ListAdapter<MovieResult, MovieListByGenreAdapter.MovieResultViewHolder>(MovieResultDiffUtil()) {


    inner class MovieResultViewHolder(val itemBinding: ItemMovieByCategorieBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movieResult: MovieResult) {
            itemBinding.movie = movieResult
            itemBinding.setClickListener {
                onClick.onMovieClick(movieResult, it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieResultViewHolder {
        val binding =
            ItemMovieByCategorieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieResultViewHolder, position: Int) {

        val movieResult = getItem(position)
        if (hasViewMore && movieResult.isViewMore) {
            holder.itemBinding.movieTitle.text = "View More"
            holder.itemBinding.movieTitle.visibility = View.VISIBLE

            holder.itemBinding.movieImg.setOnClickListener {
                onClick.onViewMoreClick(genre!!, it)
            }
        } else {
            holder.bind(movieResult)

        }


    }
}

private class MovieResultDiffUtil : DiffUtil.ItemCallback<MovieResult>() {
    override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        return newItem == oldItem

    }

}
