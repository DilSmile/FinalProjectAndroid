package com.example.finalprojectmovie.presentation.my_lists

import com.example.finalprojectmovie.domain.model.Movie
import com.example.finalprojectmovie.domain.repository.MovieRepository
import com.example.finalprojectmovie.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MyListsViewModel @Inject constructor(
    private val repository: MovieRepository
) : BaseViewModel() {

    val watchList: Flow<List<Movie>> = repository.getWatchListMovies()

}
