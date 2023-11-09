package com.dejvidleka.moviehub.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.moviehub.databinding.ItemWhatToWhatchBinding


class TopMovieAdapter :
    ListAdapter<MovieResult, TopMovieAdapter.TopMovieViewHolder>(TopMovieDiffUtil()) {
    inner class TopMovieViewHolder(itemBinding: ItemWhatToWhatchBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMovieViewHolder {
        val binding=ItemWhatToWhatchBinding.inflate(LayoutInflater.from(parent.context))
        return TopMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopMovieViewHolder, position: Int) {
    }
}

class TopMovieDiffUtil : DiffUtil.ItemCallback<MovieResult>() {
    override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        TODO("Not yet implemented")
    }

}