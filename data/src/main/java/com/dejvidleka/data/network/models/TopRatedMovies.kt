package com.dejvidleka.data.network.models

import com.google.gson.annotations.SerializedName

data class TopRatedMovies(
    val page: Int,
    @SerializedName("results")
    val movieResults: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
)
