package com.busenamli.moviesapp.ui.uistate

import androidx.paging.PagingData
import com.busenamli.moviesapp.data.model.Genre
import com.busenamli.moviesapp.data.model.Movie

data class MovieUiState(
    val isLoading: Boolean? = false,
    val movieList: PagingData<Movie>? = PagingData.empty(),
    val isError: Boolean? = false,
    val errorMessage: List<Message>? = listOf(),
    val isRefresh: Boolean? = false,
    val genreList: List<Genre>? = listOf(),
    val genreId: Int? = null,
    val isNetworkConnection: Boolean? = true
)