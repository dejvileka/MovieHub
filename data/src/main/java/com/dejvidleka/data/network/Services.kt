package com.dejvidleka.data.network

import com.dejvidleka.data.network.models.GenreResponse
import com.dejvidleka.data.network.models.MovieByGenre
import com.dejvidleka.data.network.models.MovieCast
import com.dejvidleka.data.network.models.TopRatedMovies
import com.dejvidleka.data.network.models.Trailer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Services {
    @GET("/3/discover/movie")
    suspend fun getMovies(
        @Query("api_key") appId: String,
        @Query("with_genres") genre: String,
        @Query("page") page: Int
    ): Response<MovieByGenre>

   @GET("/3/movie/top_rated")
    suspend fun getTopRated(
        @Query("api_key") appId: String
    ): Response<TopRatedMovies>


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


    @GET("/3/genre/movie/list")
    suspend fun getGenre(
        @Query("api_key") appId: String
    ): Response<GenreResponse>


}

