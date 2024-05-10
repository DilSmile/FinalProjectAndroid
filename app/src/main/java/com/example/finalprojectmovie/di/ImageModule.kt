package com.example.finalprojectmovie.di

import com.example.finalprojectmovie.presentation.image_loader.GlideImageLoader
import com.example.finalprojectmovie.presentation.image_loader.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageModule {

    @Singleton
    @Provides
    fun provideImageLoader() : ImageLoader = GlideImageLoader()
}
