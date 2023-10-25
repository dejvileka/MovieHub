package com.dejvidleka.data.network

import androidx.room.Index
import javax.inject.Inject


class MovieClient @Inject constructor(
    private val services: MoviesServices
) {
//    suspend fun getGenres()= services.getGenre()
//    suspend fun getMovies(genre: String, page: Int) = services.getMovies(genre, page,)
//    suspend fun getTopRatedMovies() = services.getTopRated()
    suspend fun getTrailerForMovie(movieId:Int) = services.getTrailer(movieId)
    suspend fun getTrailer(movieId: Int) = services.getTrailer(movieId)


}