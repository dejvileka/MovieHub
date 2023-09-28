package com.dejvidleka.moviehub.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dejvidleka.data.network.models.Cast
import com.dejvidleka.data.network.models.Genre
import com.dejvidleka.data.network.models.MovieByGenre
import com.dejvidleka.data.network.models.MovieResult
import com.dejvidleka.data.network.Services
import com.dejvidleka.moviehub.utils.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class MainViewModel @Inject constructor(
private val services: com.dejvidleka.data.network.Services

) : ViewModel() {
    private val _genre = MutableStateFlow(emptyList<com.dejvidleka.data.network.models.Genre>())

    val genre: StateFlow<List<com.dejvidleka.data.network.models.Genre>> = _genre

    private val _movie = MutableStateFlow(emptyList<com.dejvidleka.data.network.models.MovieByGenre>())

    val movie: StateFlow<List<com.dejvidleka.data.network.models.MovieByGenre>> = _movie

    val moviesByGenre: MutableStateFlow<Map<Int, List<com.dejvidleka.data.network.models.MovieResult>>> = MutableStateFlow(emptyMap())

    val allMovies: MutableStateFlow<List<com.dejvidleka.data.network.models.MovieResult>> = MutableStateFlow(emptyList())

    val castById: MutableStateFlow<Map<Int, List<com.dejvidleka.data.network.models.Cast>>> = MutableStateFlow(emptyMap())

    private val _loading = MutableStateFlow(false)
    val loading: MutableStateFlow<Boolean> get() = _loading

    private val _cast = MutableStateFlow(emptyList<com.dejvidleka.data.network.models.Cast>())

    val cast: StateFlow<List<com.dejvidleka.data.network.models.Cast>> = _cast

    private val _trailer = MutableStateFlow<String?>(null)

    val trailer: StateFlow<String?> = _trailer.asStateFlow()


    private val _error = MutableStateFlow("")
    val error: StateFlow<String?> get() = _error

    init {
        viewModelScope.launch {
            fetchGenres()
        }

    }

    private fun fetchGenres() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = services.getGenre(appId = API_KEY)
                if (response.isSuccessful && response.body() != null) {
                    _genre.value = response.body()?.genres!!
                } else {
                    _error.value = "failed to fetch generes"
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage
            }
            _loading.value = false
        }
    }

    fun fetchMoviesByGenre(genreId: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response =  services.getMovies(appId = API_KEY, genre = genreId.toString(), page = 1)
                if (response.isSuccessful) {
                    response.body()?.let { movieByGenre ->
                        val currentMap = moviesByGenre.value
                            moviesByGenre.value =  currentMap + (genreId to (movieByGenre.movieResults?: emptyList()))
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
    }

    fun fetchMovieCast(movieId: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = services.getCast(movieId = movieId, appId = API_KEY)
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
                val response = services.getTrailer(movieId = movieId, appId = API_KEY)
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
                val allMoviesList = mutableListOf<com.dejvidleka.data.network.models.MovieResult>()

                val movieFlow: Flow<List<com.dejvidleka.data.network.models.MovieResult>> = flow {
                    var currentPage = 1
                    val maxPages = 10
                    while (currentPage <= maxPages) {
                        val response = services.getMovies(appId = API_KEY, genre = genreId.toString(), page = currentPage)
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
