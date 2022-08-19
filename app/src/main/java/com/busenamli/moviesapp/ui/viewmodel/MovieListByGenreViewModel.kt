package com.busenamli.moviesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.busenamli.moviesapp.data.repository.MovieRepository
import com.busenamli.moviesapp.ui.uistate.Message
import com.busenamli.moviesapp.ui.uistate.MovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MovieListByGenreViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(MovieUiState())
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()
    private val errorList: ArrayList<Message> = arrayListOf()
    private var fetchJob: Job? = null

    init {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
    }

    fun fetchMoviesByGenre(genreId: Int) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    genreId = genreId
                )
            }
            try {
                movieRepository.fetchMoviesByGenre(genreId).cachedIn(viewModelScope)
                    .collectLatest { model ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                movieList = model,
                                genreId = genreId,
                                isError = false
                            )
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
                        movieList = null,
                        genreId = genreId,
                        errorMessage = errorList,
                        isError = true
                    )
                }
            }
        }
        fetchJob!!.start()
    }

    fun refreshList() {
        errorList.clear()
        _uiState.update { currentUiState ->
            currentUiState.copy(
                errorMessage = errorList,
                isRefresh = true
            )
        }
        val genreId = _uiState.value.genreId
        genreId?.let { fetchMoviesByGenre(genreId) }
    }

    fun errorMessageShown(error: Message) {
        errorList.removeAt(errorList.indexOf(error))
        _uiState.update { currentUiState ->
            currentUiState.copy(
                errorMessage = errorList
            )
        }
    }

    fun networkCheck(status: Boolean){
        if (!status) {
            errorList.add(
                Message(
                    errorList.size,
                    "İnternet bağlantısı bulunamadı!"
                )
            )
            _uiState.update { currentUiState ->
                currentUiState.copy(
                    isLoading = false,
                    isNetworkConnection = false,
                    isError = true,
                    errorMessage = errorList
                )
            }
        }else{
            _uiState.update { currentUiState ->
                currentUiState.copy(
                    isLoading = false,
                    isNetworkConnection = true,
                    isError = false,
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        fetchJob?.cancel()
    }
}