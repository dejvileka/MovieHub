package com.dejvidleka.moviehub.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dejvidleka.data.local.models.MovieData
import com.dejvidleka.moviehub.databinding.ItemTopMoviesBinding
import com.dejvidleka.moviehub.utils.Image_URL
import com.dejvidleka.moviehub.utils.MovieClickListener
import com.dejvidleka.moviehub.utils.getWatchProviders


class TopMovieAdapter(
    private val savedRegionCode:String?,
    private val activity: Context,
    private val onClick: MovieClickListener,
) : ListAdapter<MovieData, TopMovieAdapter.MovieResultViewHolder>(TopMovieDiffUtil()) {


    inner class MovieResultViewHolder(private val itemBinding: ItemTopMoviesBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movieResult: MovieData) {
            itemBinding.setClickListener {
                onClick.onMovieClickNew(movieResult, it)
            }
            itemBinding.movie = movieResult
            itemBinding.movieDurationLenth.text = movieResult.runtime.toString()

            savedRegionCode.let {
                val logoPath =
                    movieResult.results[savedRegionCode]?.flatrate?.firstOrNull()?.logo_path
                val providerName =
                    movieResult.results[savedRegionCode]?.flatrate?.firstOrNull()?.provider_name


            itemBinding.providerLogo.setOnClickListener {
                providerName?.let { activity.getWatchProviders(it,activity)
                }
            }

            if (!logoPath.isNullOrEmpty()) {
                val fullImageUrl = Image_URL + logoPath
                Glide.with(itemView.context).load(fullImageUrl).into(itemBinding.providerLogo)
                itemBinding.providerLogo.visibility = View.VISIBLE
                itemBinding.providerName.visibility = View.GONE
            } else {
                itemBinding.providerLogo.visibility = View.GONE
                itemBinding.providerName.visibility = View.VISIBLE
            }
        }}
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieResultViewHolder {
        val binding =
            ItemTopMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieResultViewHolder, position: Int) {

        val movieResult = getItem(position)
        holder.bind(movieResult)

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
