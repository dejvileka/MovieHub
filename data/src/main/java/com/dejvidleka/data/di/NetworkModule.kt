package com.dejvidleka.data.di

import com.dejvidleka.data.network.apiservice.MovieClient
import com.dejvidleka.data.network.apiservice.MoviesServices
import com.dejvidleka.data.repo.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.themoviedb.org")
            .addConverterFactory(GsonConverterFactory.create()) // <-- Add this
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieServices(retrofit: Retrofit): MoviesServices {
        return retrofit.create(MoviesServices::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieClient(services: MoviesServices): MovieClient {
        return MovieClient(services)
    }
}