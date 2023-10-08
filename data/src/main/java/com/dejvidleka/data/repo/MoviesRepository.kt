package com.dejvidleka.data.repo

import com.dejvidleka.data.local.dao.MovieDao
import com.dejvidleka.data.local.models.Cast
import com.dejvidleka.data.local.models.Genre
import com.dejvidleka.data.local.models.MovieEntity
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.data.local.models.SimilarMoviesResult
import com.dejvidleka.data.local.models.TrailerResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface MoviesRepository {
    val movieDao: MovieDao
    fun getMovies(genre: String, page: Int = 1): Flow<List<MovieResult>>

    fun getGenre(): Flow<List<Genre>>

    fun getCast(movieId: Int): Flow<List<Cast>>

    fun getTrailer(movieId: Int): Flow<TrailerResult>


    fun getTopRated(): Flow<List<MovieResult>>

    fun getSimilarMovies(movieId: Int): Flow<List<SimilarMoviesResult>>

    fun getAllFavoriteMovies(): Flow<List<MovieEntity>>
    suspend fun addFavorite(movie: MovieEntity) {
        withContext(Dispatchers.IO) {
            movieDao.addMovie(movie)
        }
    }

    suspend fun removeFavorite(movie: MovieEntity)


}