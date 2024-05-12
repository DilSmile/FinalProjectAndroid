package com.example.finalprojectmovie.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.finalprojectmovie.common.Const.Api.MOVIES_PAGE_SIZE
import com.example.finalprojectmovie.common.Const.Api.TRAILER
import com.example.finalprojectmovie.data.network.api.MovieApi
import com.example.finalprojectmovie.data.db.WatchListMovieDao
import com.example.finalprojectmovie.data.db.WatchListMovieEntity
import com.example.finalprojectmovie.data.network.data_source.MoviePagingSource
import com.example.finalprojectmovie.data.network.data_source.SearchMoviePagingSource
import com.example.finalprojectmovie.data.mapper.mapToDomain
import com.example.finalprojectmovie.data.mapper.mapToTrailer
import com.example.finalprojectmovie.data.mapper.mapToWatchListEntity
import com.example.finalprojectmovie.data.network.model.MovieEntity
import com.example.finalprojectmovie.data.network.model.VideoEntity
import com.example.finalprojectmovie.domain.model.Movie
import com.example.finalprojectmovie.domain.model.MovieDetails
import com.example.finalprojectmovie.domain.model.Trailer
import com.example.finalprojectmovie.domain.model.Video
import com.example.finalprojectmovie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val watchListMovieDao: WatchListMovieDao
) : MovieRepository {

    override suspend fun getPopularMovies(): List<Movie> {
        return api.getMovies(1).movies.map(MovieEntity::mapToDomain)
    }

    override suspend fun getSimilarMovies(id: Int): List<Movie> {
        return api.getSimilarMovies(id, 1).movies.map(MovieEntity::mapToDomain)
    }

    override suspend fun getMovie(id: Int): MovieDetails {
        return api.getMovieDetails(id).mapToDomain()
    }

    override suspend fun getVideos(id: Int): List<Video> {
        return api.getMovieVideos(id).videos.map(VideoEntity::mapToDomain)
    }

    override suspend fun getTrailers(id: Int): List<Trailer> {
        return api.getMovieVideos(id).videos.filter { it.type == TRAILER }
            .map(VideoEntity::mapToTrailer)
    }

    override fun getMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = MOVIES_PAGE_SIZE,
                maxSize = 200
            ),
            pagingSourceFactory = { MoviePagingSource(api) }
        ).flow
            .map { pagingData ->
                pagingData.map(MovieEntity::mapToDomain)
            }
    }

    override fun getMoviesByTitle(title: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = MOVIES_PAGE_SIZE,
                maxSize = 200
            ),
            pagingSourceFactory = { SearchMoviePagingSource(api, title) }
        ).flow
            .map { pagingData ->
                pagingData.map(MovieEntity::mapToDomain)
            }
    }

    override suspend fun getNowPlayingMovies(): List<Movie> {
        return api.getNowPlayingMovies(1).movies.map(MovieEntity::mapToDomain)
    }

    override fun getWatchListMovies(): Flow<List<Movie>> {
        return watchListMovieDao.getMoviesFlow().map { data ->
            data.map(WatchListMovieEntity::mapToDomain)
        }
    }

    override fun addToWatchList(movie: Movie) {
        watchListMovieDao.insert(movie.mapToWatchListEntity())
    }

    override fun inWatchList(id: Int): Boolean = watchListMovieDao.getMovie(id) != null

    override fun removeFromWatchList(id: Int) {
        watchListMovieDao.remove(id)
    }

}
