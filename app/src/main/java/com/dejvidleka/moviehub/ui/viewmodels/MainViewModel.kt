package com.dejvidleka.moviehub.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dejvidleka.data.local.models.Cast
import com.dejvidleka.data.local.models.Genre
import com.dejvidleka.data.local.models.MovieEntity
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.data.local.models.ProvidersResponse
import com.dejvidleka.data.local.models.TrailerResult
import com.dejvidleka.data.network.MovieClient
import com.dejvidleka.data.network.MoviesServices
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
import kotlinx.coroutines.flow.map
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
    val category: StateFlow<String> = _category

    private val _providerLogoPath= MutableStateFlow("")
    var providerLogoPath: StateFlow<String> = _providerLogoPath

    fun updatePath(path:String){
        _providerLogoPath.value=path
    }
    val providerLogoPathLiveData = providerLogoPath.asLiveData()


    private val _section = MutableStateFlow("top_rated")
    private val section: StateFlow<String> = _section

    val topRatedMovies = _category.combine(_section) { category, section ->
        Pair(category, section)
    }.flatMapLatest { (category, section) ->
        moviesRepository.getTopRated(category, section).toResult()
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

    val genre: StateFlow<Result<List<Genre>>> = _genres



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


    fun getSearchResult(query: String): Flow<Result<List<MovieResult>>> {
        return moviesRepository.getSearchResult(query).toResult()
    }

    fun getTrending(category: String): Flow<Result<List<MovieResult>>> {
        return moviesRepository.getTrending(category).toResult()
    }


    fun getTvDetails(tvId: Int): Flow<String> {
        return moviesRepository.getTvDetails(tvId).map { details ->
            "${details.number_of_episodes}"
        }
    }


}