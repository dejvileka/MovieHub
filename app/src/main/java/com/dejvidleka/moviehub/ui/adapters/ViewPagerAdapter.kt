package com.dejvidleka.moviehub.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.network.models.MovieResult
import com.dejvidleka.moviehub.databinding.ItemPosterBinding

class ViewPagerAdapter() :
    ListAdapter<MovieResult, ViewPagerAdapter.ViewPagerViewHolder>(MovieResultDiffUtil()) {


    inner class ViewPagerViewHolder(private val itemBinding: ItemPosterBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movieResult: MovieResult) {
            itemBinding.movie = movieResult
            itemBinding.clickListener = View.OnClickListener {
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding = ItemPosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {

        val movieResult = getItem(position)

        holder.bind(movieResult)


    }

    class MovieResultDiffUtil : DiffUtil.ItemCallback<MovieResult>() {
        override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
            return newItem == oldItem

        }

    }
}