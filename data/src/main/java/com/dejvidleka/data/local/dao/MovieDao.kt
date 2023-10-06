package com.dejvidleka.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dejvidleka.data.network.models.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie:MovieEntity)

    @Delete
    suspend fun delete(movie:MovieEntity)

    @Query("SELECT * FROM favorite_movies")
    fun getAllFavoriteMovies(): Flow<List<MovieEntity>>
}