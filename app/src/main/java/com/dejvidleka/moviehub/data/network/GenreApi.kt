package com.dejvidleka.moviehub.data.network

import com.dejvidleka.moviehub.data.model.GenreResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreApi{
    @GET ("/3/genre/movie/list")
    suspend fun getGenre(
        @Query("api_key") appId:String
    ):Response<GenreResponse>


}