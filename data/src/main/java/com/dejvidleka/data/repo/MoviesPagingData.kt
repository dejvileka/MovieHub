package com.dejvidleka.data.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dejvidleka.data.local.models.MovieData
import com.dejvidleka.data.local.models.toMovieData
import com.dejvidleka.data.network.MoviesServices
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

class MoviesPagingData(
    private val networkService: MoviesServices,
    private val category: String,
) :
    PagingSource<Int, MovieData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieData> {
        return try {
            val page = params.key ?: 1
            val movies = networkService.getRecommendedMovies(
                category,
                page
            ).movieResults.map { it.toMovieData() }
            coroutineScope {
                val semaphore = Semaphore(permits = 5)
                movies.map { movie ->
                    semaphore.withPermit {
                        val providersDeferred =
                            async { networkService.getProviders(category, movie.id) }
                        val detailsDefered =
                            async { networkService.getDetails(category, movie.id) }
                        movie.results = providersDeferred.await().results
                        movie.runtime = detailsDefered.await().runtime
                    }
                }
            }
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
