package com.example.finalprojectmovie.domain.repository

import androidx.paging.PagingData
import com.example.finalprojectmovie.domain.model.Movie
import com.example.finalprojectmovie.domain.model.MovieDetails
import com.example.finalprojectmovie.domain.model.Trailer
import com.example.finalprojectmovie.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getPopularMovies(): List<Movie>

    suspend fun getMovie(id: Int): MovieDetails

    suspend fun getVideos(id: Int): List<Video>

    suspend fun getTrailers(id: Int): List<Trailer>

    suspend fun getSimilarMovies(id: Int): List<Movie>

    fun getMovies(): Flow<PagingData<Movie>>

    fun getMoviesByTitle(title: String): Flow<PagingData<Movie>>

    suspend fun getNowPlayingMovies(): List<Movie>

    fun getWatchListMovies(): Flow<List<Movie>>

    fun addToWatchList(movie: Movie)

    fun inWatchList(id: Int): Boolean

    fun removeFromWatchList(id: Int)
}
