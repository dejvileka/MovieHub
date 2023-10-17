package com.dejvidleka.moviehub.utils

import android.view.View
import com.dejvidleka.data.local.models.Genre
import com.dejvidleka.data.local.models.MovieResult

interface OnMovieClickListener {
    fun onMovieClick(movieResult: MovieResult, view: View)
    fun onViewMoreClick(genre: Genre, view: View) // If needed
}