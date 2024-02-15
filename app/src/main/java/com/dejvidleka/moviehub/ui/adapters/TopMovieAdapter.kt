package com.dejvidleka.moviehub.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.local.models.MovieData
import com.dejvidleka.moviehub.databinding.ItemTopMoviesBinding
import com.dejvidleka.moviehub.utils.MovieClickListener


class TopMovieAdapter(
    private val onClick: MovieClickListener
    ) : ListAdapter<MovieData, TopMovieAdapter.MovieResultViewHolder>(TopMovieDiffUtil()) {


    inner class MovieResultViewHolder(val itemBinding: ItemTopMoviesBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movieResult: MovieData) {
            itemBinding.setClickListener {
                onClick.onMovieClickNew(movieResult, it)
            }
            itemBinding.movie = movieResult
            itemBinding.movieDurationLenth.text = movieResult.runtime.toString()
            val providerName =
                movieResult.results["US"]?.flatrate?.first()?.provider_name
            itemBinding.prpviderName.text = providerName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieResultViewHolder {
        val binding =
            ItemTopMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieResultViewHolder, position: Int) {

        val movieResult = getItem(position)
        holder.bind(movieResult)
//        holder.itemBinding.movieImg.setOnClickListener {
//            onClick.onMovieClick(movieResult, it)
//        }

    }
}


private class TopMovieDiffUtil : DiffUtil.ItemCallback<MovieData>() {
    override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
        return newItem == oldItem

    }

}
