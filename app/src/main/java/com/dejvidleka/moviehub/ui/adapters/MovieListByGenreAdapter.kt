package com.dejvidleka.moviehub.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.moviehub.R
import com.dejvidleka.moviehub.data.model.MovieResult
import com.dejvidleka.moviehub.databinding.ItemMovieByCategorieBinding
import com.dejvidleka.moviehub.ui.home.FirstFragmentDirections
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel


class MovieListByGenreAdapter(private val viewModel: MainViewModel) : ListAdapter<MovieResult, MovieListByGenreAdapter.MovieResultViewHolder>(MovieResultDiffUtil()) {


    inner class MovieResultViewHolder(val itemBinding: ItemMovieByCategorieBinding) : RecyclerView.ViewHolder(itemBinding.root) {
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

    private fun navigateToMoreMovies(view: View) {
        val directions = FirstFragmentDirections.actionFirstFragmentToMoreMoviesPerGenre()
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
                navigateToMoreMovies(it)

            }// Use your "View More" image here
        } else {
            holder.bind(movieResult)

        }
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
