package com.dejvidleka.data.repo

import androidx.paging.PagingData
import com.dejvidleka.data.local.dao.MovieDao
import com.dejvidleka.data.local.models.Cast
import com.dejvidleka.data.local.models.Genre
import com.dejvidleka.data.local.models.MovieData
import com.dejvidleka.data.local.models.MovieEntity
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.data.local.models.Regions
import com.dejvidleka.data.local.models.Result
import com.dejvidleka.data.local.models.TrailerResult
import com.dejvidleka.data.local.models.TvDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

interface MoviesRepository {
    val movieDao: MovieDao

    fun getMoviesStream(category: String): Flow<PagingData<MovieData>>

    fun getMovies(category: String,genre: String, page: Int = 1): Flow<List<MovieResult>>
//    fun recommendedMovies(category: StateFlow<String>, page: Int): Flow<PagingData<MovieData>>

    fun getGenre(category: String): Flow<List<Genre>>

    fun getCast(movieId: Int): Flow<List<Cast>>

    fun getTrailer(movieId: Int): Flow<TrailerResult>

    fun getTopRated(category: String,section: String): Flow<List<MovieData>>
    fun getRegions(): Flow<List<Regions>>
    fun getProviderNames(): Flow<List<Result>>
    fun getTrending(category: String): Flow<List<MovieResult>>

    fun getSimilarMovies(movieId: Int): Flow<List<MovieResult>>
    fun getTvDetails(movieId: Int): Flow<TvDetails>

    fun getAllFavoriteMovies(): Flow<List<MovieEntity>>
    suspend fun addFavorite(movie: MovieEntity) {
        withContext(Dispatchers.IO) {
            movieDao.addMovie(movie)
        }
    }

    suspend fun removeFavorite(movie: MovieEntity)

    fun getSearchResult(query:String):Flow<List<MovieResult>>



}