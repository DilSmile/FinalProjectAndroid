package com.example.finalprojectmovie.di

import com.example.finalprojectmovie.data.db.WatchListMovieDao
import com.example.finalprojectmovie.data.network.api.MovieApi
import com.example.finalprojectmovie.data.repository.MovieRepositoryImpl
import com.example.finalprojectmovie.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        api: MovieApi,
        watchListMovieDao: WatchListMovieDao
    ): MovieRepository = MovieRepositoryImpl(api, watchListMovieDao)
}
