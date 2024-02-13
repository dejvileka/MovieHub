package com.dejvidleka.data.di

import com.dejvidleka.data.network.MovieClient
import com.dejvidleka.data.network.MoviesServices
import com.dejvidleka.data.repo.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.dnsoverhttps.DnsOverHttps
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val cacheDir = File("cacheDir", "okhttpcache") // Adjust the cache directory as needed
        val appCache = Cache(cacheDir, 10 * 1024 * 1024) // 10 MB cache

        val bootstrapClient = OkHttpClient.Builder().cache(appCache).build()

        val dns = DnsOverHttps.Builder().client(bootstrapClient)
            .url("https://1.1.1.1/dns-query".toHttpUrl()) // Using Cloudflare's DNS over HTTPS
            .build()

        return bootstrapClient.newBuilder()
            .dns(dns)
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