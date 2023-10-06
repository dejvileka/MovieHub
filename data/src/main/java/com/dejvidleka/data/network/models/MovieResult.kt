package com.dejvidleka.data.network.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResult(
    val adult: Boolean,
    val backdrop_path: String?=null,
    val genre_ids: List<Int>,
    @PrimaryKey
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    var isViewMore: Boolean = false
) : Parcelable

fun MovieResult.toEntity(): MovieEntity {
    return MovieEntity(
        adult,
        backdrop_path,
        id,
        original_language,
        original_title,
        overview,
        popularity,
        poster_path,
        release_date,
        title,
        video,
        vote_average,
        vote_count,
        isViewMore,
    )
}

@Entity(tableName = "favorite_movies")
data class MovieEntity(
    val adult: Boolean,
    val backdrop_path: String? = null,
    @PrimaryKey
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    var isViewMore: Boolean = false
)


fun MovieResult.toDomain(): MovieResult {
    return MovieResult(
        adult,
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
        video,
        vote_average,
        vote_count,
        isViewMore
    )
}