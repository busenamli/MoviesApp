package com.busenamli.moviesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busenamli.moviesapp.data.repository.MovieDetailRepository
import com.busenamli.moviesapp.ui.uistate.MovieDetailUiState
import com.busenamli.moviesapp.data.api.NetworkResult
import com.busenamli.moviesapp.ui.uistate.Message
import com.busenamli.moviesapp.ui.uistate.ScrollState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val moviesDetailRepository: MovieDetailRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()
    private val errorList: ArrayList<Message> = arrayListOf()
    private var fetchJob: Job? = null
    private var fetchJobCredits: Job? = null

    init {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
    }

    fun fetchMovieDetail(movieId: Int) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val response = moviesDetailRepository.fetchMovieDetail(movieId)
                when (response) {
                    is NetworkResult.Success -> {
                        fetchMovieCredit(movieId)
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                movie = response.data,
                                isError = false
                            )
                        }
                    }
                    is NetworkResult.Error -> {
                        errorList.add(Message(errorList.size, "Sayfa yüklenirken hata oluştu!"))
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                errorMessage = errorList
                            )
                        }
                    }
                }
            } catch (e: IOException) {
                errorList.add(
                    Message(
                        errorList.size,
                        "Sayfa yüklenirken hata oluştu! Tekrar deneyin!"
                    )
                )
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

    fun errorMessageShown(error: Message) {
        errorList.removeAt(errorList.indexOf(error))
        _uiState.update { currentUiState ->
            currentUiState.copy(
                errorMessage = errorList
            )
        }
    }

    fun genreSelected(genreId: Int) {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                selectedGenre = genreId
            )
        }
    }

    fun savePageScrollState(position: ScrollState) {
        _uiState.update {
            it.copy(
                pageScrollState = position
            )
        }
    }

    fun saveCreditListScrollState(position: Int) {
        _uiState.update {
            it.copy(
                creditListScrollState = position
            )
        }
    }

    fun fetchMovieCredit(movieId: Int) {
        fetchJobCredits?.cancel()
        fetchJobCredits = viewModelScope.launch {
            try {
                val response = moviesDetailRepository.fetchMovieCredit(movieId)
                when (response) {
                    is NetworkResult.Success -> {
                        _uiState.update {
                            it.copy(
                                cast = response.data.castModel
                            )
                        }
                    }
                    is NetworkResult.Error -> {
                        errorList.add(
                            Message(
                                errorList.size,
                                "Oyuncular yüklenirken hata oluştu!"
                            )
                        )
                        _uiState.value = MovieDetailUiState(
                            cast = null,
                            errorMessage = errorList
                        )
                    }
                }
            } catch (e: IOException) {
                errorList.add(
                    Message(
                        errorList.size,
                        "Oyuncular yüklenirken hata oluştu! Tekrar deneyin!"
                    )
                )
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