package com.dejvidleka.moviehub.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform


sealed interface Result<T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error<T>(val error: Throwable?) : Result<T>
    class Loading<T> : Result<T>
}


fun <T> Flow<T>.toResult(): Flow<Result<T>> {
    return this.map<T, Result<T>> { value ->
        Result.Success(value)
    }.onStart {
        emit(Result.Loading())
    }.catch { ex ->
        emit(Result.Error(ex))
    }
}


