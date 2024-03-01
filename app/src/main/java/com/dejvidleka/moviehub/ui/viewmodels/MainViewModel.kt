package com.dejvidleka.moviehub.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dejvidleka.data.local.models.Cast
import com.dejvidleka.data.local.models.Genre
import com.dejvidleka.data.local.models.MovieEntity
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.data.local.models.Regions
import com.dejvidleka.data.local.models.TrailerResult
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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private val _category = MutableStateFlow("movie")
    val category: StateFlow<String> = _category
    private val _section = MutableStateFlow("")
    val section: StateFlow<String> = _section
    private val _page = MutableStateFlow(1)


    val recommendedMovies = _category.flatMapLatest { category ->
        _page.flatMapLatest { pageNum ->
            moviesRepository.recommendedMovies(category, _page.value).toResult().stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = Result.Loading()
            )
        }
    }

    val topRatedMovies = _category.combine(section) { category, section ->
        Pair(category, section)
    }.flatMapLatest { (category, section) ->
        moviesRepository.getTopRated(category, section).toResult()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = Result.Loading(),
        )
    fun getTrending(category: String): Flow<Result<List<MovieResult>>> {
        return moviesRepository.getTrending(category).toResult()
    }

    private val _genres = _category.flatMapLatest { category ->
        moviesRepository.getGenre(category).toResult()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = Result.Loading()
    )

    val genre: StateFlow<Result<List<Genre>>> = _genres
    private val _regions= moviesRepository.getRegions().toResult()
    val regions: Flow<Result<List<Regions>>> = _regions

    fun updateCategory(category: String) {
        _category.value = category
    }

    fun updateSection(section: String) {
        _section.value = section
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

    fun getProviderNames(): Flow<Result<List<com.dejvidleka.data.local.models.Result>>> {
        return moviesRepository.getProviderNames().toResult()
    }

    fun getSearchResult(query: String): Flow<Result<List<MovieResult>>> {
        return moviesRepository.getSearchResult(query).toResult()
    }

}