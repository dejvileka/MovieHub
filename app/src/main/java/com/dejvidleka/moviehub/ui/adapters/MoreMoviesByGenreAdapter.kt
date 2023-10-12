package com.dejvidleka.moviehub.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.moviehub.databinding.ItemAllMoviesByCategorieBinding
import com.dejvidleka.moviehub.ui.home.FirstFragmentDirections


class MoreMoviesByGenreAdapter() :
    ListAdapter<MovieResult, MoreMoviesByGenreAdapter.MoreMovieResultViewHolder>(MoreMovieResultDiffUtil()) {


    inner class MoreMovieResultViewHolder(val itemBinding: ItemAllMoviesByCategorieBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movieResult: MovieResult) {
            itemBinding.movie = movieResult
            itemBinding.clickListener = View.OnClickListener {
                navigateToDetails(movieResult, it)
            }


        }

        private fun navigateToDetails(movieResult: MovieResult, view: View) {
            val directions = FirstFragmentDirections.actionHomeToMovieDetail(movieResult)
            view.findNavController().navigate(directions)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreMovieResultViewHolder {
        val binding = ItemAllMoviesByCategorieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoreMovieResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoreMovieResultViewHolder, position: Int) {

        val movieResult = getItem(position)

        holder.bind(movieResult)


    }

}

private class MoreMovieResultDiffUtil : DiffUtil.ItemCallback<MovieResult>() {
    override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        return newItem == oldItem

    }

}
