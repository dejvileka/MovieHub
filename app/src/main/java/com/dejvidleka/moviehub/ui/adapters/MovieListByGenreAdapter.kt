package com.dejvidleka.moviehub.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.moviehub.R
import com.dejvidleka.data.network.models.Genre
import com.dejvidleka.data.network.models.MovieResult
import com.dejvidleka.moviehub.databinding.ItemMovieByCategorieBinding
import com.dejvidleka.moviehub.ui.home.FirstFragmentDirections
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel


class MovieListByGenreAdapter(
    private val viewModel: MainViewModel,
    private val genre: com.dejvidleka.data.network.models.Genre
) : ListAdapter<com.dejvidleka.data.network.models.MovieResult, MovieListByGenreAdapter.MovieResultViewHolder>(MovieResultDiffUtil()) {


    inner class MovieResultViewHolder(val itemBinding: ItemMovieByCategorieBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movieResult: com.dejvidleka.data.network.models.MovieResult) {
            itemBinding.movie = movieResult
            itemBinding.clickListener = View.OnClickListener {
                navigateToDetails(movieResult, it)
            }


        }

        private fun navigateToDetails(movieResult: com.dejvidleka.data.network.models.MovieResult, view: View) {
            val directions = FirstFragmentDirections.actionHomeToMovieDetail(movieResult)
            view.findNavController().navigate(directions)
        }

    }

    private fun navigateToMoreMovies(genre: com.dejvidleka.data.network.models.Genre, view: View) {
        val directions = FirstFragmentDirections.actionFirstFragmentToMoreMoviesPerGenre(genre)
        view.findNavController().navigate(directions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieResultViewHolder {
        val binding = ItemMovieByCategorieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieResultViewHolder, position: Int) {

        val movieResult = getItem(position)
        if (movieResult.isViewMore) {
            holder.itemBinding.movieTitle.text = "View More"
            holder.itemBinding.movieImg.setImageResource(R.drawable.moviehublogo)
            holder.itemBinding.movieImg.setOnClickListener {
                navigateToMoreMovies(genre, it)
            }
        } else {
            holder.bind(movieResult)

        }
    }

}

class MovieResultDiffUtil : DiffUtil.ItemCallback<com.dejvidleka.data.network.models.MovieResult>() {
    override fun areItemsTheSame(oldItem: com.dejvidleka.data.network.models.MovieResult, newItem: com.dejvidleka.data.network.models.MovieResult): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: com.dejvidleka.data.network.models.MovieResult, newItem: com.dejvidleka.data.network.models.MovieResult): Boolean {
        return newItem == oldItem

    }

}
