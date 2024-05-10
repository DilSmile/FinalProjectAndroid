package com.example.finalprojectmovie.presentation.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.finalprojectmovie.domain.model.Movie
import com.example.finalprojectmovie.domain.repository.MovieRepository
import com.example.finalprojectmovie.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
) : BaseViewModel() {

    var movies: Flow<PagingData<Movie>> = flowOf()

    fun search(text: String) {
        movies = repository.getMoviesByTitle(text).cachedIn(viewModelScope)
    }

}
