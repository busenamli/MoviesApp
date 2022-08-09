package com.busenamli.moviesapp.ui.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busenamli.moviesapp.ui.uistate.MovieDetailUiState
import com.busenamli.moviesapp.data.repository.MovieDetailRepositoryImpl
import com.busenamli.moviesapp.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val moviesDetailRepositoryImpl: MovieDetailRepositoryImpl): ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()
    private var fetchJob: Job? = null

    fun fetchMovieDetail(movieId: Int){
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                moviesDetailRepositoryImpl.fetchMovieDetail(movieId).collect{networkResult->
                    _uiState.value = MovieDetailUiState(
                        isLoading = true,
                        movie = null,
                        error = null
                    )
                    when(networkResult){
                        is NetworkResult.Success -> {
                            _uiState.value = MovieDetailUiState(
                                isLoading = false,
                                movie = networkResult.data,
                                error = null
                            )
                        }
                        is NetworkResult.Error -> {
                            _uiState.value = MovieDetailUiState(
                                isLoading = false,
                                movie = null,
                                error = networkResult.exception
                            )
                        }
                    }
                }
            }catch (e: IOException){
                MovieDetailUiState(
                    isLoading = false,
                    error = e.message
                )
            }
        }
        fetchJob!!.start()
    }

    fun fetchMovieCredit(movieId: Int) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                moviesDetailRepositoryImpl.fetchMovieCredit(movieId).collect{networkResult->
                    when(networkResult){
                        is NetworkResult.Success -> {
                            _uiState.value = MovieDetailUiState(
                                isLoading = false,
                                credit = networkResult.data
                            )
                        }
                        is NetworkResult.Error -> {
                            _uiState.value = MovieDetailUiState(
                                isLoading = false,
                                credit = null,
                                error = networkResult.exception
                            )
                        }
                    }
                }
            }catch (e: IOException){
                MovieDetailUiState(
                    isLoading = false,
                    credit = null,
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