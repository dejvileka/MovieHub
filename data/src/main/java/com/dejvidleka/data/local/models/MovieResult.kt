package com.dejvidleka.data.local.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResult(
    val adult: Boolean,
    val backdrop_path: String?=null,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String?= null ?:"",
    val original_title: String?= null ?:"",
    val overview: String?= null ?:"",
    val popularity: Double,
    val poster_path: String?= null ?:"",
    val release_date: String?= null ?:"",
    val title: String?= null ?:"",
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    var isViewMore: Boolean = false
) : Parcelable

fun MovieEntity.toMovieResult(): MovieResult {
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

fun MovieResult.toEntity(): MovieEntity {
    return MovieEntity(
        id,
        adult,
        backdrop_path,
        genre_ids,
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
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String? = null,
    val genre_ids: List<Int>,
    val original_language: String?= null ?:"",
    val original_title: String?=null ?: "" ,
    val overview: String?= null ?:"",
    val popularity: Double,
    val poster_path: String?= null ?:"",
    val release_date: String?= null ?:"",
    val title: String?= null ?:"",
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

