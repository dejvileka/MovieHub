package com.dejvidleka.moviehub.data.network

import com.dejvidleka.moviehub.data.model.MovieCast
import com.dejvidleka.moviehub.data.model.Result
import com.dejvidleka.moviehub.data.model.Trailer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieCastCrewApi {
    @GET("/3/movie/{movie_id}/credits")
    suspend fun getCast(
        @Path("movie_id") movieId: Int,
        @Query("api_key") appId: String
    ): Response<MovieCast>

    @GET("/3/movie/{movie_id}/videos")
    suspend fun getTrailer(
        @Path("movie_id") movieId: Int,
        @Query("api_key") appId: String
        ): Response<Trailer>
    }


