package com.dejvidleka.data.network.models

data class MovieCast(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)