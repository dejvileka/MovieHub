package com.dejvidleka.moviehub.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.moviehub.data.model.MovieResult
import com.dejvidleka.moviehub.databinding.ItemMovieByCategorieBinding
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel


class MovieListByGenreAdapter(private val viewModel: MainViewModel) : ListAdapter<MovieResult, MovieListByGenreAdapter.MovieResultViewHolder>(MovieResultDiffUtil()) {


    inner class MovieResultViewHolder(private val itemBinding: ItemMovieByCategorieBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(movieResult: MovieResult) {
            itemBinding.movie = movieResult
            itemBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieResultViewHolder {
        val binding = ItemMovieByCategorieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieResultViewHolder, position: Int) {
        val movieResult = getItem(position)
        holder.bind(movieResult)
    }

}

class MovieResultDiffUtil : DiffUtil.ItemCallback<MovieResult>() {
    override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        return newItem == oldItem

    }

}
