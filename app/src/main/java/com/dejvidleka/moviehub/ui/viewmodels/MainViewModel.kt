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
import com.dejvidleka.data.network.models.Trailer
import com.dejvidleka.data.network.models.TrailerResult
import com.dejvidleka.data.repo.MoviesRepository
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.domain.toResult
import com.dejvidleka.moviehub.utils.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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



    val allMovies: MutableStateFlow<List<MovieResult>> = MutableStateFlow(emptyList())

    private val _loading = MutableStateFlow(false)

    private val _error = MutableStateFlow("")

    val genres = moviesRepository.getGenre().toResult()

    val topMovies = moviesRepository.getTopRated().toResult()

    private val _selectedGenreId = MutableStateFlow<String?>(null)


    fun setGenre(genreId: String) {
        _selectedGenreId.value = genreId
    }

    fun moviesForGenre(genreId: String, page: Int=1): Flow<Result<List<MovieResult>>> {
        return moviesRepository.getMovies(genreId,page).toResult()
    }

    fun castForMovie(movieId: Int): Flow<Result<List<Cast>>> {
        return moviesRepository.getCast(movieId).toResult()
    }

    fun trailerForMovie(movieId: Int): Flow<Result<TrailerResult>> {
        return moviesRepository.getTrailer(movieId).toResult()
    }

    fun allMoviesForGenre(genreId: String, page: Int): Flow<Result<List<MovieResult>>> {
        val movieFlow: Flow<List<MovieResult>> = flow {
            var currentPage = 1
            val maxPages = 10
            while (currentPage <= maxPages) {
                val response = moviesRepository.getMovies(genre = genreId, page = currentPage)
                Log.d("API_RESPONSE", "Response: ${response}")
                currentPage++
            }

        }
        return movieFlow.toResult()
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

