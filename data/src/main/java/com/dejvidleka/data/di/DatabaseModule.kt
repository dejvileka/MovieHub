package com.dejvidleka.data.di

import android.app.Application
import androidx.room.Room
import com.dejvidleka.data.local.dao.MovieDao
import com.dejvidleka.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "movie_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(
        database: AppDatabase
    )
    : MovieDao {
        return database.movieDao()
    }
}

