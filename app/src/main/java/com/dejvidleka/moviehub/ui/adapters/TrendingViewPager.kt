package com.dejvidleka.moviehub.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.moviehub.databinding.ItemTrendingMoviesBinding
import com.dejvidleka.moviehub.utils.MovieClickListener

class TrendingViewPager(val onClick: MovieClickListener) :
    ListAdapter<MovieResult, TrendingViewPager
    .ViewPagerViewHolder>(MovieResultDiffUtil()) {


    inner class ViewPagerViewHolder(private val itemBinding: ItemTrendingMoviesBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movieResult: MovieResult) {
            itemBinding.movie = movieResult
            itemBinding.movieImg.setOnClickListener {
                onClick.onMovieClick(movieResult, it)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding = ItemTrendingMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val movieResult = getItem(position)
        holder.bind(movieResult)

    }

    class MovieResultDiffUtil : DiffUtil.ItemCallback<MovieResult>() {
        override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
            return newItem == oldItem

        }

    }
}