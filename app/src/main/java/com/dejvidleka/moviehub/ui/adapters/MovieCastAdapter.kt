package com.dejvidleka.moviehub.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.local.models.Cast
import com.dejvidleka.moviehub.databinding.ItemCastCrewBinding

class MovieCastAdapter : ListAdapter<Cast, MovieCastAdapter.MovieClassViewHolder>(CastDiffUtil()) {

    inner class MovieClassViewHolder(private val itemBinding: ItemCastCrewBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(cast: Cast) {
            itemBinding.cast = cast
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieClassViewHolder {
        val binding = ItemCastCrewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieClassViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieClassViewHolder, position: Int) {
        val castResult = getItem(position)
        holder.bind(castResult)
    }
}

private class CastDiffUtil : DiffUtil.ItemCallback<Cast>() {
    override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
    return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
    return oldItem ==newItem
    }
}