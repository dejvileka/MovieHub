package com.dejvidleka.moviehub.data.model

import com.google.gson.annotations.SerializedName

data class MovieByGenre(
    val page: Int,
    @SerializedName("results")
    val movieResults: List<MovieResult>?,
    val total_pages: Int,
    val total_results: Int
)