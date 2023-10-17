package com.dejvidleka.moviehub.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.moviehub.R
import com.dejvidleka.data.local.models.Genre
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.moviehub.databinding.ItemMovieByCategorieBinding
import com.dejvidleka.moviehub.ui.home.FirstFragmentDirections


class MovieListByGenreAdapter(
    private val onItemClick: (MovieResult, View) -> Unit

) : ListAdapter<MovieResult, MovieListByGenreAdapter.MovieResultViewHolder>(MovieResultDiffUtil()) {


    inner class MovieResultViewHolder(val itemBinding: ItemMovieByCategorieBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movieResult: MovieResult) {
            itemBinding.movie = movieResult
            itemBinding.root.setOnClickListener { view ->
                onItemClick(movieResult, view)
            }
        }
    }

    /*      private fun navigateToDetails(movieResult: MovieResult, view: View) {
              val directions = FirstFragmentDirections.actionHomeToMovieDetail(movieResult)
              view.findNavController().navigate(directions)
          }
      }

      private fun navigateToMoreMovies(genre: Genre, view: View) {
          val directions = FirstFragmentDirections.actionFirstFragmentToMoreMoviesPerGenre(genre)
          view.findNavController().navigate(directions)
      }
  */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieResultViewHolder {
        val binding =
            ItemMovieByCategorieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieResultViewHolder, position: Int) {

        val movieResult = getItem(position)
        if (movieResult.isViewMore) {
            holder.itemBinding.movieTitle.text = "View More"
            holder.itemBinding.movieTitle.visibility = View.VISIBLE

            holder.itemBinding.root.setOnClickListener { view ->
                onItemClick(movieResult, view)
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
