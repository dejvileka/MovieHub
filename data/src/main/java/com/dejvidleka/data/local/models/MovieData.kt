package com.dejvidleka.data.local.models

data class MovieData(
    val backdrop_path: String? = null,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String? = null ?: "",
    val original_title: String? = null ?: "",
    val overview: String? = null ?: "",
    val popularity: Double,
    val poster_path: String? = null ?: "",
    val release_date: String? = null ?: "",
    val title: String? = null ?: "",
    val vote_average: Double,
    val vote_count: Int,
    var isViewMore: Boolean = false,
    var runtime: Int = -1,
    var results: Map<String, ProviderDetails> = emptyMap(),
)

fun MovieResult.toMovieData() = MovieData(
    backdrop_path,
    genre_ids,
    id,
    original_language,
    original_title,
    overview,
    popularity,
    poster_path,
    release_date,
    title,
    vote_average,
    vote_count,
    isViewMore
)

