package com.dejvidleka.moviehub.utils

import android.view.View
import com.dejvidleka.data.local.models.Genre
import com.dejvidleka.data.local.models.MovieData
import com.dejvidleka.data.local.models.MovieResult

interface MovieClickListener {
    fun onMovieClick(movieResult: MovieResult, view: View)
    fun onMovieClickNew(movieData: MovieData, view: View)
    fun onViewMoreClick(genre: Genre, view: View)
}