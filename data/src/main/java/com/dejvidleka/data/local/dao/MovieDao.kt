package com.dejvidleka.data.local.dao

import kotlinx.coroutines.flow.Flow
import androidx.room.*
import com.dejvidleka.data.local.models.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun addMovie(movie: MovieEntity)

    @Delete
     fun deleteMovie(movie: MovieEntity)

    @Query("SELECT * FROM favorite_movies")
    fun getAllFavoriteMovies(): Flow<List<MovieEntity>>
}