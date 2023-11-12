package com.dejvidleka.moviehub.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.moviehub.databinding.ItemTrendingMoviesBinding

class TrendingCarosel :
    ListAdapter<MovieResult, TrendingCarosel.TrendingViewHolder>(TrendingDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrendingCarosel.TrendingViewHolder {
        val binding = ItemTrendingMoviesBinding.inflate(LayoutInflater.from(parent.context))
        return TrendingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrendingCarosel.TrendingViewHolder, position: Int) {
        val movieResult = getItem(position)
        holder.bind(movieResult)
    }


    inner class TrendingViewHolder(val binding: ItemTrendingMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieResult: MovieResult) {
           binding.movie= movieResult
        }
    }
}

private class TrendingDiffUtil : DiffUtil.ItemCallback<MovieResult>() {
    override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        return oldItem == newItem
    }

}
