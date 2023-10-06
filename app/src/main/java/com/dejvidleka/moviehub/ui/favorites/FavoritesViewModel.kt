package com.dejvidleka.moviehub.ui.favorites

import androidx.lifecycle.ViewModel
import com.dejvidleka.data.network.apiservice.MovieClient
import com.dejvidleka.data.network.apiservice.MoviesServices
import com.dejvidleka.data.network.models.MovieEntity
import com.dejvidleka.data.repo.MoviesRepository
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.domain.toResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel  @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val services: MoviesServices,
    private val movieClient: MovieClient,
) : ViewModel() {

    fun getAllFavoriteMovies(): Flow<Result<List<MovieEntity>>> {
        return moviesRepository.getAllFavoriteMovies().toResult()
    }
}