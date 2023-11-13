package com.dejvidleka.data.network

import com.dejvidleka.data.local.models.GenreResponse
import com.dejvidleka.data.local.models.MovieByGenre
import com.dejvidleka.data.local.models.MovieCast
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.data.local.models.SearchResultMovies
import com.dejvidleka.data.local.models.SimilarMovies
import com.dejvidleka.data.local.models.TopRatedMovies
import com.dejvidleka.data.local.models.Trailer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesServices {
    @GET("/3/discover/{category}")
    suspend fun getMovies(
        @Path("category") category: String,
        @Query("with_genres") genre: String,
        @Query("page") page: Int
    ): Response<MovieByGenre>

    @GET("/3/trending/all/day")
    suspend fun getTrending(
//        @Path("section") category: String,
    ): Response<MovieByGenre>

    @GET("/3/{category}/{section}")
    suspend fun getTopRated(
        @Path("category") category: String,
        @Path("section") section:String
    ): Response<TopRatedMovies>


    @GET("/3/movie/{movie_id}/credits")
    suspend fun getCast(
        @Path("movie_id") movieId: Int,
    ): Response<MovieCast>

    @GET("/3/movie/{movie_id}/videos")
    suspend fun getTrailer(
        @Path("movie_id") movieId: Int
    ): Response<Trailer>


    @GET("/3/genre/{category}/list")
    suspend fun getGenre(
        @Path("category") category: String,
        ): Response<GenreResponse>

    @GET("/3/movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int
    ): Response<SimilarMovies>

    @GET("/3/search/movie")
    suspend fun getSearchResult(
        @Query("query") query: String
    ): Response<SearchResultMovies>
}

