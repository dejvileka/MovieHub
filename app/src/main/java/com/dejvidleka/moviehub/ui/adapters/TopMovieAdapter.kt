package com.dejvidleka.moviehub.ui.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import com.google.android.material.internal.ContextUtils.getActivity


class TopMovieAdapter(
    private val activity: Context,
    private val onClick: MovieClickListener,
) : ListAdapter<MovieData, TopMovieAdapter.MovieResultViewHolder>(TopMovieDiffUtil()) {


    inner class MovieResultViewHolder(val itemBinding: ItemTopMoviesBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movieResult: MovieData) {
            itemBinding.setClickListener {
                onClick.onMovieClickNew(movieResult, it)
            }
            itemBinding.movie = movieResult
            itemBinding.movieDurationLenth.text = movieResult.runtime.toString()
            val logoPath = movieResult.results["AL"]?.flatrate?.firstOrNull()?.logo_path

            itemBinding.providerLogo.setOnClickListener {
                OpenNFX(movieResult.title.toString())
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
        }
    }

    fun OpenNFX(movieTitle:String) {
        val netflix = Intent()
        netflix.setAction(Intent.ACTION_VIEW)
        netflix.setData(Uri.parse("https://www.netflix.com/search?q=${movieTitle}"))
        netflix.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        activity.startActivity(netflix)
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
