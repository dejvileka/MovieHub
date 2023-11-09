package com.dejvidleka.moviehub.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.dejvidleka.data.network.MoviesServices
import com.dejvidleka.data.local.models.Cast
import com.dejvidleka.data.local.models.Genre
import com.dejvidleka.data.local.models.MovieEntity
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.data.local.models.TrailerResult
import com.dejvidleka.data.network.MovieClient
import com.dejvidleka.data.repo.MoviesRepository
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.domain.toResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val services: MoviesServices,
    private val movieClient: MovieClient,
) : ViewModel() {


    private val _category = MutableStateFlow("movie")
    private val category: StateFlow<String> = _category


    private val _section= MutableStateFlow("upcoming")
    private val section: StateFlow<String> = _category


    private val _topRatedMovies = _category.flatMapLatest { category ->
        moviesRepository.getTopRated(category).toResult()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = Result.Loading()
    )


    private val _genres = _category.flatMapLatest { category ->
        moviesRepository.getGenre(category).toResult()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = Result.Loading()
    )


    val topRatedMovies: StateFlow<Result<List<MovieResult>>> = _topRatedMovies
    val genre: StateFlow<Result<List<Genre>>> = _genres

    fun updateCategory(category: String) {
        _category.value = category

    }

    fun addFavorite(movie: MovieEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            moviesRepository.addFavorite(movie)
        }
    }

    suspend fun removeFavorite(movie: MovieEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            moviesRepository.removeFavorite(movie)
        }
    }


    private val _selectedGenreId = MutableStateFlow<String?>(null)


    fun setGenre(genreId: String) {
        _selectedGenreId.value = genreId
    }

    fun moviesForGenre(genreId: String, page: Int = 1): Flow<Result<List<MovieResult>>> {
        return category.flatMapLatest {
            moviesRepository.getMovies(it, genreId, page).toResult()
        }
    }

    fun castForMovie(movieId: Int): Flow<Result<List<Cast>>> {
        return moviesRepository.getCast(movieId).toResult()
    }

    fun trailerForMovie(movieId: Int): Flow<Result<TrailerResult>> {
        return moviesRepository.getTrailer(movieId).toResult()
    }

    fun getSimilarMovies(movieId: Int): Flow<Result<List<MovieResult>>> {
        return moviesRepository.getSimilarMovies(movieId).toResult()
    }
    fun getSearchResult(query: String):Flow<Result<List<MovieResult>>>{
        return moviesRepository.getSearchResult(query).toResult()
    }
}

