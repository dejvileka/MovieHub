package com.dejvidleka.data.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dejvidleka.data.local.models.MovieData
import kotlinx.coroutines.flow.StateFlow

class MoviesPagingData(
    private val moviesRepository: MoviesRepository,
    private val category: StateFlow<String>,
) :
    PagingSource<Int, MovieData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieData> {
        return try {
            val page = params.key ?: 1
            val data = moviesRepository.recommendedMovies(category, page) as List<MovieData>
            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
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
