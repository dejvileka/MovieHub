package com.dejvidleka.moviehub.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class MovieByGenre(
    val page: Int,
    @SerializedName("results")
    val movieResults: List<MovieResult>?,
    val total_pages: Int,
    val total_results: Int
)

