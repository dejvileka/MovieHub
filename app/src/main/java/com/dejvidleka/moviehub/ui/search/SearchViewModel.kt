package com.dejvidleka.moviehub.ui.search

import androidx.lifecycle.ViewModel
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.data.repo.MoviesRepository
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.domain.toResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,

    ) : ViewModel() {


    fun getSearchResult(query: String): Flow<Result<List<MovieResult>>> {
        return moviesRepository.getSearchResult(query).toResult()
    }

}