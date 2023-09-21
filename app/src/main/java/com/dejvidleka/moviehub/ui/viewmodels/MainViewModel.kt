package com.dejvidleka.moviehub.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dejvidleka.moviehub.data.model.Genre
import com.dejvidleka.moviehub.data.model.MovieByGenre
import com.dejvidleka.moviehub.data.model.MovieResult
import com.dejvidleka.moviehub.data.network.GenreApi
import com.dejvidleka.moviehub.data.network.MoviesApi
import com.dejvidleka.moviehub.utils.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class MainViewModel @Inject constructor(
    private val genreApi: GenreApi,
    private val moviesApi: MoviesApi

) : ViewModel() {
    private val _genre = MutableStateFlow(emptyList<Genre>())
    val genre: StateFlow<List<Genre>> = _genre
    private val _movie = MutableStateFlow(emptyList<MovieByGenre>())
    val movie: StateFlow<List<MovieByGenre>> = _movie

    val moviesByGenre: MutableStateFlow<Map<Int, List<MovieResult>>> = MutableStateFlow(emptyMap())

    private val _loading = MutableStateFlow(false)
    val loading: MutableStateFlow<Boolean> get() = _loading

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
                val response = genreApi.getGenre(appId = API_KEY)
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
                val response = moviesApi.getMovies(appId = API_KEY, genre = genreId.toString())
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
    }



}