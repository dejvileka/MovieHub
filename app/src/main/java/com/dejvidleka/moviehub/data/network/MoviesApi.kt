package com.dejvidleka.moviehub.data.network

import com.dejvidleka.moviehub.data.model.MovieByGenre
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("/3/discover/movie")
    suspend fun getMovies(
        @Query("api_key") appId: String,
        @Query("with_genres") genre: String,
    ): Response<MovieByGenre>

}
