package com.dejvidleka.moviehub.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dejvidleka.data.network.apiservice.MovieClient
import com.dejvidleka.data.network.apiservice.MoviesServices
import com.dejvidleka.data.network.models.Cast
import com.dejvidleka.data.network.models.Genre
import com.dejvidleka.data.network.models.MovieByGenre
import com.dejvidleka.data.network.models.MovieResult
import com.dejvidleka.data.repo.MoviesRepository
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.domain.toResult
import com.dejvidleka.moviehub.utils.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class MainViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val services: MoviesServices,
    private val movieClient: MovieClient
) : ViewModel() {
//    private val _genre = MutableStateFlow(emptyList<Genre>())
//
//    val genre: StateFlow<List<Genre>> = _genre

    private val _movie = MutableStateFlow(emptyList<MovieByGenre>())

    val movie: StateFlow<List<MovieByGenre>> = _movie

    val allMovies: MutableStateFlow<List<MovieResult>> = MutableStateFlow(emptyList())

    val castById: MutableStateFlow<Map<Int, List<Cast>>> = MutableStateFlow(emptyMap())

    private val _loading = MutableStateFlow(false)
    val loading: MutableStateFlow<Boolean> get() = _loading

    private val _cast = MutableStateFlow(emptyList<com.dejvidleka.data.network.models.Cast>())

    val cast: StateFlow<List<Cast>> = _cast

    private val _trailer = MutableStateFlow<String?>(null)

    val trailer: StateFlow<String?> = _trailer.asStateFlow()


    private val _error = MutableStateFlow("")
    val error: StateFlow<String?> get() = _error

    val genres = moviesRepository.getGenre().toResult()
    val trailers = moviesRepository.getTrailer(1).toResult()
    val movieCast = moviesRepository.getCast(1).toResult()
    val topMovies = moviesRepository.getTopRated().toResult()

    private val _selectedGenreId = MutableStateFlow<String?>(null)

    val moviesByGenre: Flow<Result<List<MovieResult>>> = _selectedGenreId.flatMapLatest { genreId ->
        if (genreId != null) {
            moviesRepository.getMovies(genreId).toResult()
        } else {
            flowOf(Result.Error(Throwable("No genre selected")))
        }
    }

    fun setGenre(genreId: String) {
        _selectedGenreId.value = genreId
    }

    fun moviesForGenre(genreId: String): Flow<Result<List<MovieResult>>> {
        return moviesRepository.getMovies(genreId).toResult()
    }

/*    fun fetchMoviesByGenre(genreId: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = services.getMovies(genre = genreId.toString(), page = 1)
                if (response.isSuccessful) {
                    response.body()?.let { movieByGenre ->
                        val currentMap = moviesByGenre.value
                        moviesByGenre.value = currentMap + (genreId to (movieByGenre.movieResults ?: emptyList()))
                    } ?: run {
                        _error.value = "Movie response body is null"
                    }
                } else {
                    _error.value = "failed to fetch movies"
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage
            }
            _loading.value = false
        }

    }*/

 /*   fun fetchTopRatedMovies() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = movieClient.getTopRatedMovies()
                if (response.isSuccessful) {
                    response.body()?.let { moviesResponse ->
                        topMovies.value = moviesResponse.movieResults.sortedBy { it.title }
                        Log.d("API_RESPONSE", "Response: $response")

                    } ?: run {
                        _error.value = "Movie response body is null"
                    }
                } else {
                    _error.value = "failed to fetch movies"
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage
            }
            _loading.value = false
        }
    }*/


    fun fetchMovieCast(movieId: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = services.getCast(movieId = movieId)
                if (response.isSuccessful) {
                    response.body()?.let { movieCast ->
                        val currentMap = castById.value
                        castById.value = currentMap + (movieId to (movieCast.cast))
                    } ?: run {
                        _error.value = "Cast response body is null"
                    }
                } else {
                    _error.value = "Failed to fetch"
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage
            }
            _loading.value = false
        }
    }

    fun fetchTrailerForMovie(movieId: Int) {

        viewModelScope.launch {
            _loading.value = true
            try {
                val response = services.getTrailer(movieId = movieId)
                if (response.isSuccessful) {
                    val trailer = response.body()
                    val key = trailer?.results?.firstOrNull()?.key
                    _trailer.value = key
                } else {
                    _error.value = "Failed to fetch"
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage

            }
        }
    }

    fun fetchAllMoviesByGenre(genreId: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val allMoviesList = mutableListOf<MovieResult>()
                val movieFlow: Flow<List<MovieResult>> = flow {
                    var currentPage = 1
                    val maxPages = 10
                    while (currentPage <= maxPages) {
                        val response = services.getMovies(genre = genreId.toString(), page = currentPage)
                        Log.d("API_RESPONSE", "Response: $response")

                        if (response.isSuccessful) {
                            response.body()?.let { allMoviesByGenreResponse ->
                                emit(allMoviesByGenreResponse.movieResults ?: emptyList())
                            }
                        }
                        currentPage++
                    }
                }
                movieFlow.collect { movies ->
                    allMoviesList.addAll(movies)
                }

                allMovies.value = allMoviesList

            } catch (e: Exception) {
                _error.value = e.localizedMessage
            } finally {
                _loading.value = false
            }
        }
    }
}

