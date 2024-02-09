package com.dejvidleka.data.repo

import com.dejvidleka.data.local.dao.MovieDao
import com.dejvidleka.data.network.MoviesServices
import com.dejvidleka.data.local.models.Cast
import com.dejvidleka.data.local.models.Genre
import com.dejvidleka.data.local.models.MovieDetails
import com.dejvidleka.data.local.models.MovieEntity
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.data.local.models.TrailerResult
import com.dejvidleka.data.local.models.TvDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesService: MoviesServices,
    override val movieDao: MovieDao
) : MoviesRepository {

    override fun getMovies(categry: String,genre: String, page: Int): Flow<List<MovieResult>> {
        return flow {
            val response = moviesService.getMovies(categry, "301766,18321,445,7344,33451,33432,284609,267122,280017,155477",genre, page)
            emit(response.body()?.movieResults ?: emptyList())
        }
    }

    override fun getGenre(categry: String): Flow<List<Genre>> {
        return flow {
            val response = moviesService.getGenre(categry)
            emit(response.body()?.genres ?: emptyList())
        }
    }

    override fun getCast(movieId: Int): Flow<List<Cast>> {
        return flow {
            val response = moviesService.getCast(movieId)
            emit(response.body()?.cast ?: emptyList())
        }
    }

    override fun getTrailer(movieId: Int): Flow<TrailerResult> {
        return flow {
            val response = moviesService.getTrailer(movieId)
            val trailer = response.body()
            if (trailer != null) {
                emit(trailer.results.first())
            } else {

            }
        }
    }

    override fun getTopRated(category: String, section:String): Flow<List<MovieResult>> {
        return flow {
            val response = moviesService.getTopRated(category, section)
            emit(response.body()?.movieResults ?: emptyList())
        }
    }

    override fun getTrending(category: String): Flow<List<MovieResult>> {
        return flow {
            val response = moviesService.getTrending(category)
            emit(response.body()?.movieResults ?: emptyList())
        }
    }

    override fun getSimilarMovies(movieId: Int): Flow<List<MovieResult>> {
        return flow {
            val response = moviesService.getSimilarMovies(movieId)
            emit(response.body()?.results ?: emptyList())
        }
    }

    override fun getMovieDetails(movieId: Int): Flow<MovieDetails>{
        return flow {
            val response= moviesService.getDetails(movieId)
            response.body()?.let { emit(it) }
        }
    }
    override fun getTvDetails(tvId: Int): Flow<TvDetails>{
        return flow {
            val response= moviesService.getDetailsTv(tvId)
            response.body()?.let { emit(it) }
        }
    }

    override fun getAllFavoriteMovies(): Flow<List<MovieEntity>> {
      return  movieDao.getAllFavoriteMovies()
    }


    override suspend fun addFavorite(movie: MovieEntity) {
        movieDao.addMovie(movie)
    }

    override suspend fun removeFavorite(movie: MovieEntity) {
        movieDao.deleteMovie(movie)
    }

    override fun getSearchResult(query: String): Flow<List<MovieResult>> {
        return flow {
            val response = moviesService.getSearchResult(query)
            emit(response.body()?.results ?: emptyList())
        }
    }


}