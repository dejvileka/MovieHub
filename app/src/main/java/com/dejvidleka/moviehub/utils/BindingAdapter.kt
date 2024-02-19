package com.dejvidleka.moviehub.utils

import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val baseURL = "https://image.tmdb.org/t/p/w500"
        val fullURL = baseURL + it
        Glide
            .with(imgView.context)
            .load(fullURL)
            .into(imgView)
    }
}

@BindingAdapter("imageUrl")
fun bindImageProvider(imageView: ImageView, imageUrl: LiveData<String>?) {
    imageUrl?.value?.let { url ->
        Glide.with(imageView.context).load(url).into(imageView)
    }
}


@BindingAdapter("maxCharacters")
fun TextView.maxCharacters(maxCharacters: Int) {
    var text = text.toString()
    if (text.length > maxCharacters) {
        text = text.substring(0, maxCharacters) + "..."
        this.text = text
    }
}

@BindingAdapter("android:rating")
fun setRating(view: RatingBar, rating: Double) {
    view.rating = rating.toFloat() / 2
}
