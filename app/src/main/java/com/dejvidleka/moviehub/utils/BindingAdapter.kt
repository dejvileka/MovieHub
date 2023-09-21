package com.dejvidleka.moviehub.utils

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val baseURL = "https://image.tmdb.org/t/p/w500"
        val fullURL = baseURL + it
        Glide.with(imgView.context)
            .load(fullURL)
            .into(imgView)
    }
}