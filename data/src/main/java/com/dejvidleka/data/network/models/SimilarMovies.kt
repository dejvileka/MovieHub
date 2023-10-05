package com.dejvidleka.data.network.models

data class SimilarMovies(
    val page: Int,
    val results: List<SimilarMoviesResult>,
    val total_pages: Int,
    val total_results: Int
)