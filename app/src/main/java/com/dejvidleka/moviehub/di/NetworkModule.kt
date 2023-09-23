package com.dejvidleka.moviehub.di

import com.dejvidleka.moviehub.data.network.GenreApi
import com.dejvidleka.moviehub.data.network.MovieCastCrewApi
import com.dejvidleka.moviehub.data.network.MoviesApi
import com.dejvidleka.moviehub.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGenreApi(retrofit: Retrofit): GenreApi {
        return retrofit.create(GenreApi::class.java)
    }
    @Provides
    @Singleton
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }
    @Provides
    @Singleton
    fun provideMovieCast(retrofit: Retrofit): MovieCastCrewApi{
        return retrofit.create(MovieCastCrewApi::class.java)
    }


}