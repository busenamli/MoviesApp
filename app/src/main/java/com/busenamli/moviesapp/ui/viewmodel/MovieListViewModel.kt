package com.busenamli.moviesapp.ui.viewmodel

import androidx.lifecycle.*
import androidx.paging.*
import com.busenamli.moviesapp.data.repository.MovieRepository
import com.busenamli.moviesapp.data.api.NetworkResult
import com.busenamli.moviesapp.ui.uistate.Message
import com.busenamli.moviesapp.ui.uistate.MovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

    private val _uiState = MutableStateFlow(MovieUiState())
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()
    private val errorList: ArrayList<Message> = arrayListOf()
    private var fetchJob: Job? = null
    private var fetchJobGenre: Job? = null

    init {
        fetchPopularMovies()
        fetchGenreList()
    }

    fun fetchPopularMovies(){
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    isError = false,
                    movieList = null
                )
            }
            delay(5000)
            try {
                movieRepository.fetchPopularMovies().cachedIn(viewModelScope).collectLatest {model->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            movieList = model,
                            isError = false
                        )
                    }
                }
            } catch (e: IOException) {
                errorList.add(Message(errorList.size,"Sayfa yüklenirken hata oluştu! Tekrar deneyin!"))
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        movieList = null,
                        isError = true,
                        errorMessage = errorList
                    )
                }
            }
        }
        fetchJob!!.start()
    }

    fun fetchGenreList(){
        fetchJobGenre?.cancel()
        fetchJobGenre = viewModelScope.launch {
            try {
                movieRepository.fetchGenreList().collect { networkResult ->
                    when(networkResult) {
                        is NetworkResult.Success -> {
                            _uiState.update {
                                it.copy(
                                    genreList = networkResult.data.genreList
                                )
                            }
                        }
                        is NetworkResult.Error -> {
                            errorList.add(Message(errorList.size,"Sayfa yüklenirken hata oluştu!"))
                            _uiState.update {
                                it.copy(
                                    genreList = null,
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
                        genreList = null,
                        isError = true,
                        errorMessage = errorList
                    )
                }
            }
        }
        fetchJobGenre?.start()
    }

    fun refreshList(){
        errorList.clear()
        _uiState.update {currentUiState->
            currentUiState.copy(
                errorMessage = errorList
            )
        }
        fetchPopularMovies()
        fetchGenreList()
    }

    fun errorMessageShown(){
        errorList.removeAt(errorList.size-1)
        _uiState.update {currentUiState->
            currentUiState.copy(
                errorMessage = errorList
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        fetchJob?.cancel()
        fetchJobGenre?.cancel()
    }
}