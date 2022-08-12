package com.busenamli.moviesapp.ui.uistate

import androidx.paging.PagingData
import com.busenamli.moviesapp.data.model.GenreModel
import com.busenamli.moviesapp.data.model.MovieModel

data class MovieUiState (
    val isLoading: Boolean? = false,
    val movieList: PagingData<MovieModel>? = PagingData.empty(),
    val isError: Boolean? = false,
    val errorMessage: List<Message>? = listOf(),
    val isRefresh: Boolean? = false,
    val genreList: List<GenreModel>? = listOf(),
    val genreId: Int? = null,
)