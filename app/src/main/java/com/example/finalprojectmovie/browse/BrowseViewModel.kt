package com.example.finalprojectmovie.presentation.screen.browse

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.finalprojectmovie.domain.model.Movie
import com.example.finalprojectmovie.domain.repository.MovieRepository
import com.example.finalprojectmovie.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val repository: MovieRepository
) : BaseViewModel() {

    val movies: Flow<PagingData<Movie>> = repository.getMovies()
        .cachedIn(viewModelScope)

}
