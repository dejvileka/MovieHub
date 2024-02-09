package com.dejvidleka.moviehub.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.moviehub.databinding.ItemTopMoviesBinding
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import com.dejvidleka.moviehub.utils.MovieClickListener
import kotlinx.coroutines.launch


class TopMovieAdapter(
    private val mainViewModel: MainViewModel,
    private val onClick: MovieClickListener,
    private val lifecycleOwner: LifecycleOwner,

    ) : ListAdapter<MovieResult, TopMovieAdapter.MovieResultViewHolder>(TopMovieDiffUtil()) {


    inner class MovieResultViewHolder(val itemBinding: ItemTopMoviesBinding) :
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
            ItemTopMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieResultViewHolder, position: Int) {

        val movieResult = getItem(position)
        holder.bind(movieResult)
        holder.itemBinding.movieImg.setOnClickListener {
            onClick.onMovieClick(movieResult, it)
        }
        if (mainViewModel.category.value == "movie") {
            lifecycleOwner.lifecycleScope.launch {
                mainViewModel.getMovieDetails(movieResult.id).collect {
                    holder.itemBinding.movieDurationLenth.text = it
                }
            }
        } else {
            lifecycleOwner.lifecycleScope.launch {
                mainViewModel.getTvDetails(movieResult.id).collect {
                    holder.itemBinding.movieDurationLenth.text = it
                    holder.itemBinding.movieDuration.text = "Number of episodes:"
                }
            }
        }
    }
}


private class TopMovieDiffUtil : DiffUtil.ItemCallback<MovieResult>() {
    override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        return newItem == oldItem

    }

}
