package com.dejvidleka.moviehub.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dejvidleka.data.network.apiservice.MovieClient
import com.dejvidleka.data.network.apiservice.MoviesServices
import com.dejvidleka.data.network.models.Cast
import com.dejvidleka.data.network.models.MovieEntity
import com.dejvidleka.data.network.models.MovieResult
import com.dejvidleka.data.network.models.SimilarMoviesResult
import com.dejvidleka.data.network.models.TrailerResult
import com.dejvidleka.data.repo.MoviesRepository
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.domain.toResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class MainViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val services: MoviesServices,
    private val movieClient: MovieClient,
) : ViewModel() {


    fun addFavorite(movie: MovieEntity) {
        viewModelScope.launch {
            moviesRepository.addFavorite(movie)
        }
    }

    fun removeFavorite(movie: MovieEntity) {
        viewModelScope.launch {
            moviesRepository.removeFavorite(movie)
        }
    }

    fun getAllFavoriteMovies(): Flow<Result<List<MovieEntity>>> {
        return moviesRepository.getAllFavoriteMovies().toResult()
    }



    val genres = moviesRepository.getGenre().toResult()

    val topMovies = moviesRepository.getTopRated().toResult()

    private val _selectedGenreId = MutableStateFlow<String?>(null)


    fun setGenre(genreId: String) {
        _selectedGenreId.value = genreId
    }

    fun moviesForGenre(genreId: String, page: Int = 1): Flow<Result<List<MovieResult>>> {
        return moviesRepository.getMovies(genreId, page).toResult()
    }

    fun castForMovie(movieId: Int): Flow<Result<List<Cast>>> {
        return moviesRepository.getCast(movieId).toResult()
    }

    fun trailerForMovie(movieId: Int): Flow<Result<TrailerResult>> {
        return moviesRepository.getTrailer(movieId).toResult()
    }

    fun getSimilarMovies(movieId: Int): Flow<Result<List<SimilarMoviesResult>>> {
        return moviesRepository.getSimilarMovies(movieId).toResult()
    }


}

