package com.dejvidleka.data.network.apiservice

import com.dejvidleka.data.network.models.GenreResponse
import com.dejvidleka.data.network.models.MovieByGenre
import com.dejvidleka.data.network.models.MovieCast
import com.dejvidleka.data.network.models.SimilarMovies
import com.dejvidleka.data.network.models.TopRatedMovies
import com.dejvidleka.data.network.models.Trailer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesServices {
    @GET("/3/discover/movie")
    suspend fun getMovies(
        @Query("with_genres") genre: String,
        @Query("page") page: Int
    ): Response<MovieByGenre>

    @GET("/3/movie/top_rated")
    suspend fun getTopRated(
    ): Response<TopRatedMovies>


    @GET("/3/movie/{movie_id}/credits")
    suspend fun getCast(
        @Path("movie_id") movieId: Int,
    ): Response<MovieCast>

    @GET("/3/movie/{movie_id}/videos")
    suspend fun getTrailer(
        @Path("movie_id") movieId: Int
    ): Response<Trailer>


    @GET("/3/genre/movie/list")
    suspend fun getGenre(
    ): Response<GenreResponse>

    @GET("/3/movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int
    ): Response<SimilarMovies>

}

