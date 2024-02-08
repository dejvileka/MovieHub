package com.dejvidleka.data.repo

import com.dejvidleka.data.local.dao.MovieDao
import com.dejvidleka.data.local.models.Cast
import com.dejvidleka.data.local.models.Genre
import com.dejvidleka.data.local.models.MovieEntity
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.data.local.models.TrailerResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

interface MoviesRepository {
    val movieDao: MovieDao
    fun getMovies(category: String,genre: String, page: Int = 1): Flow<List<MovieResult>>

    fun getGenre(category: String): Flow<List<Genre>>

    fun getCast(movieId: Int): Flow<List<Cast>>

    fun getTrailer(movieId: Int): Flow<TrailerResult>


    fun getTopRated(category: String,section: String): Flow<List<MovieResult>>
    fun getTrending(category: String): Flow<List<MovieResult>>

    fun getSimilarMovies(movieId: Int): Flow<List<MovieResult>>

    fun getAllFavoriteMovies(): Flow<List<MovieEntity>>
    suspend fun addFavorite(movie: MovieEntity) {
        withContext(Dispatchers.IO) {
            movieDao.addMovie(movie)
        }
    }

    suspend fun removeFavorite(movie: MovieEntity)

    fun getSearchResult(query:String):Flow<List<MovieResult>>


}