package com.busenamli.moviesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busenamli.moviesapp.data.repository.MovieDetailRepository
import com.busenamli.moviesapp.ui.uistate.MovieDetailUiState
import com.busenamli.moviesapp.data.api.NetworkResult
import com.busenamli.moviesapp.ui.uistate.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val moviesDetailRepository: MovieDetailRepository): ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()
    private val errorList: ArrayList<Message> = arrayListOf()
    private var fetchJob: Job? = null
    private var fetchJobCredits: Job? = null

    fun fetchMovieDetail(movieId: Int){
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    isError = false
                )
            }
            try {
                moviesDetailRepository.fetchMovieDetail(movieId).collect{networkResult->
                    when(networkResult){
                        is NetworkResult.Success -> {
                            fetchMovieCredit(movieId)
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    movie = networkResult.data,
                                    isError = false
                                )
                            }
                        }
                        is NetworkResult.Error -> {
                            errorList.add(Message(errorList.size,"Sayfa yüklenirken hata oluştu!"))
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    isError = true,
                                    errorMessage = errorList
                                )
                            }
                        }
                    }
                }
            }catch (e: IOException){
                errorList.add(Message(errorList.size,"Sayfa yüklenirken hata oluştu! Tekrar deneyin!"))
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = errorList
                    )
                }
            }
        }
        fetchJob!!.start()
    }

    fun errorMessageShown(){
        errorList.removeAt(errorList.size-1)
        _uiState.update {currentUiState->
            currentUiState.copy(
                errorMessage = errorList
            )
        }
    }

    fun genreSelected(genreId: Int){
        _uiState.update { currentUiState ->
            currentUiState.copy(
                selectedGenre = genreId
            )
        }
    }

    fun fetchMovieCredit(movieId: Int) {
        fetchJobCredits?.cancel()
        fetchJobCredits = viewModelScope.launch {
            try {
                moviesDetailRepository.fetchMovieCredit(movieId).collectLatest {networkResult->
                    when(networkResult){
                        is NetworkResult.Success -> {
                            _uiState.update {
                                it.copy(
                                    cast = networkResult.data.castModel
                                )
                            }
                        }
                        is NetworkResult.Error -> {
                            errorList.add(Message(errorList.size,"Oyuncular yüklenirken hata oluştu!"))
                            _uiState.value = MovieDetailUiState(
                                cast = null,
                                errorMessage = errorList
                            )
                        }
                    }
                }
            }catch (e: IOException){
                errorList.add(Message(errorList.size,"Oyuncular yüklenirken hata oluştu! Tekrar deneyin!"))
                MovieDetailUiState(
                    cast = null,
                    errorMessage = errorList
                )
            }
        }
        fetchJobCredits!!.start()
    }

    override fun onCleared() {
        super.onCleared()
        fetchJobCredits?.cancel()
        fetchJob?.cancel()
    }
}