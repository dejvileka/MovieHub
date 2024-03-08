package com.dejvidleka.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
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
import com.dejvidleka.data.local.models.toMovieData
import com.dejvidleka.data.network.MoviesServices
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesService: MoviesServices,
    override val movieDao: MovieDao
) : MoviesRepository {

    override fun getMoviesStream(category: String): Flow<PagingData<MovieData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPagingData(moviesService, category) }
        ).flow
    }
    override fun getMovies(categry: String, genre: String, page: Int): Flow<List<MovieResult>> {
        return flow {
            val response = moviesService.getMovies(
                categry,
                "301766,18321,445,7344,33451,33432,284609,267122,280017,155477",
                genre,
                page
            )
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


    override fun getTopRated(category: String, section:String): Flow<List<MovieData>> {
        return flow {
            val movies = moviesService.getTopRated(category, section).movieResults.map {
                it.toMovieData()}
                coroutineScope {
                    val semaphore = Semaphore(permits = 5)
                    movies.map { movie ->
                        launch {
                            semaphore.withPermit {
                                val providersDeferred = async { moviesService.getProviders(category, movie.id) }
                                val detailsDeferred = async { moviesService.getDetails(category,movie.id) }
                                movie.results = providersDeferred.await().results
                                movie.runtime = detailsDeferred.await().runtime
                            }
                        }
                    }.joinAll()
                }
            emit(movies)
        }
    }
/*    override fun recommendedMovies(category: StateFlow<String>, page: Int): Flow<PagingData<MovieData>> {
        return flow {
           val movies= moviesService.getRecommendedMovies(category.value, page).movieResults.map {
                it.toMovieData()}
            coroutineScope {
                val semaphore = Semaphore(permits = 5)
                movies.map { movie ->
                    semaphore.withPermit {
                        val providersDeferred =
                            async { moviesService.getProviders(category.value, movie.id) }
                        val detailsDefered = async { moviesService.getDetails(category.value, movie.id) }
                        movie.results = providersDeferred.await().results
                        movie.runtime = detailsDefered.await().runtime
                    }

                }
            }
            emit(movies)
        }
    }*/


    override fun getTrending(category: String): Flow<List<MovieResult>> {
        return flow {
            val response = moviesService.getTrending(category)
            emit(response.body()?.movieResults ?: emptyList())
        }
    }



    override fun getProviderNames(): Flow<List<Result>> {
        return flow {
            val response = moviesService.getProvidersName()
            emit(response.body()?.results ?: emptyList())
        }
    }
    override fun getRegions(): Flow<List<Regions>> {
        return flow {
            val response = moviesService.getRegions()
            emit(response.body()?.results ?: emptyList())
        }
    }
    override fun getSimilarMovies(movieId: Int): Flow<List<MovieResult>> {
        return flow {
            val response = moviesService.getSimilarMovies(movieId)
            emit(response.body()?.results ?: emptyList())
        }
    }

    override fun getTvDetails(tvId: Int): Flow<TvDetails> {
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