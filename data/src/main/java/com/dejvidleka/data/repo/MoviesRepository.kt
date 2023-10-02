package com.dejvidleka.data.repo

import com.dejvidleka.data.network.models.Cast
import com.dejvidleka.data.network.models.Genre
import com.dejvidleka.data.network.models.MovieResult
import com.dejvidleka.data.network.models.Trailer
import com.dejvidleka.data.network.models.TrailerResult
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovies(genre: String, page: Int = 1): Flow<List<MovieResult>>

    fun getGenre(): Flow<List<Genre>>

    fun getCast(movieId:Int):Flow<List<Cast>>

    fun getTrailer(movieId: Int):Flow<TrailerResult>


    fun getTopRated():Flow<List<MovieResult>>

}