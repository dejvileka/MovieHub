package com.dejvidleka.data.di

import com.dejvidleka.data.repo.MoviesRepository
import com.dejvidleka.data.repo.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository
}