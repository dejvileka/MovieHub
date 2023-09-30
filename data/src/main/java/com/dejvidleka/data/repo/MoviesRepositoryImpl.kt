package com.dejvidleka.data.repo

import com.dejvidleka.data.network.apiservice.MoviesServices
import com.dejvidleka.data.network.models.Cast
import com.dejvidleka.data.network.models.Genre
import com.dejvidleka.data.network.models.MovieResult
import com.dejvidleka.data.network.models.Result
import com.dejvidleka.data.network.models.Trailer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesService: MoviesServices
) : MoviesRepository {

    override fun getMovies(genre: String, page: Int): Flow<List<MovieResult>> {
        return flow {
            val response = moviesService.getMovies(genre, page)
            emit(response.body()?.movieResults ?: emptyList())
        }
    }

    override fun getGenre(): Flow<List<Genre>> {
        return flow {
            val response = moviesService.getGenre()
            emit(response.body()?.genres ?: emptyList())
        }
    }

    override fun getCast(movieId: Int): Flow<List<Cast>> {
        return flow {
            val response = moviesService.getCast(movieId)
            emit(response.body()?.cast ?: emptyList())
        }
    }

    override fun getTrailer(movieId: Int): Flow<Trailer> {
        return flow {
            val response = moviesService.getTrailer(movieId)
            val trailer = response.body()
            if (trailer != null) {
                emit(trailer)
            } else {

            }
        }
    }

    override fun getTopRated(): Flow<List<MovieResult>> {
        return flow {
            val response = moviesService.getTopRated()
            emit(response.body()?.movieResults ?: emptyList())
        }
    }


}