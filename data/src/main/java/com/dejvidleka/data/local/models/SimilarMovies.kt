package com.dejvidleka.data.local.models

data class SimilarMovies(
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
)