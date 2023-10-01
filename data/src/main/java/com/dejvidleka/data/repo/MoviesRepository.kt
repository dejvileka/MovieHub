package com.dejvidleka.data.repo

import com.dejvidleka.data.network.models.Cast
import com.dejvidleka.data.network.models.Genre
import com.dejvidleka.data.network.models.MovieResult
import com.dejvidleka.data.network.models.Result
import com.dejvidleka.data.network.models.Trailer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface MoviesRepository {
    fun getMovies(genre: String, page: Int = 1): Flow<List<MovieResult>>

    fun getGenre(): Flow<List<Genre>>

    fun getCast(movieId:Int):Flow<List<Cast>>

    fun getTrailer(movieId: Int):Flow<Trailer>


    fun getTopRated():Flow<List<MovieResult>>

}