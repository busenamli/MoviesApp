package com.busenamli.moviesapp.ui.movielist

import androidx.lifecycle.*
import androidx.paging.*
import com.busenamli.moviesapp.data.repository.MovieRepositoryImpl
import com.busenamli.moviesapp.ui.uistate.MovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val moviesRepositoryImpl: MovieRepositoryImpl): ViewModel() {

    private val _uiState = MutableStateFlow(MovieUiState())
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()
    private var fetchJob: Job? = null

    init {
        fetchPopularMovies()
    }

    fun fetchPopularMovies(){
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                moviesRepositoryImpl.getPopularMovies().cachedIn(viewModelScope).collectLatest {
                    _uiState.value = MovieUiState(
                        isLoading = false,
                        movieList = it,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = MovieUiState(
                    isLoading = false,
                    movieList = null,
                    error = e.message
                )
            }
        }
        fetchJob!!.start()
    }

    override fun onCleared() {
        super.onCleared()
        fetchJob?.cancel()
    }
}