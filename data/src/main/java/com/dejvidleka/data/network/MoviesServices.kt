package com.dejvidleka.data.network

import com.dejvidleka.data.local.models.Countries
import com.dejvidleka.data.local.models.GenreResponse
import com.dejvidleka.data.local.models.MovieByGenre
import com.dejvidleka.data.local.models.MovieCast
import com.dejvidleka.data.local.models.MovieData
import com.dejvidleka.data.local.models.MovieDetails
import com.dejvidleka.data.local.models.ProvidersName
import com.dejvidleka.data.local.models.ProvidersResponse
import com.dejvidleka.data.local.models.SearchResultMovies
import com.dejvidleka.data.local.models.SimilarMovies
import com.dejvidleka.data.local.models.TopRatedMovies
import com.dejvidleka.data.local.models.Trailer
import com.dejvidleka.data.local.models.TvDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesServices {
    @GET("/3/discover/{category}")
    suspend fun getMovies(
        @Path("category") category: String,
        @Query("without_keywords") withoutKeywords: String,
        @Query("with_genres") genre: String,
        @Query("page") page: Int
    ): Response<MovieByGenre>

    @GET("/3/{category}/{section}")
    suspend fun getTopRated(
        @Path("category") category: String,
        @Path("section") section: String
    ): TopRatedMovies
    @GET("/3/discover/{category}")
    suspend fun getRecommendedMovies(
        @Path ("category") category: String,
        @Query("page") page: Int
    ): TopRatedMovies

    @GET("/3/trending/{category}/day")
    suspend fun getTrending(
        @Path("category") category: String
    ): Response<MovieByGenre>

    @GET("/3/movie/{movie_id}/credits")
    suspend fun getCast(
        @Path("movie_id") movieId: Int,
    ): Response<MovieCast>

    @GET("/3/{category}/{movie_id}")
    suspend fun getDetails(
     @Path("category") category: String,
        @Path("movie_id") movieId: Int,
    ): MovieDetails

    @GET("/3/tv/{tv_id}")
    suspend fun getDetailsTv(
        @Path("tv_id") movieId: Int,
    ): Response<TvDetails>

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


    @GET("/3/{category}/{movie_id}/watch/providers")
    suspend fun getProviders(
        @Path("category") category: String,
        @Path("movie_id") movieId: Int
    ): ProvidersResponse

    @GET("/3/watch/providers/regions")
    suspend fun getRegions(): Response<Countries>

    @GET("/3/watch/providers/movie")
    suspend fun getProvidersName(): Response<ProvidersName>

}

