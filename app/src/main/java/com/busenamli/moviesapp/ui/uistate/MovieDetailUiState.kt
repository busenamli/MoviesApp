package com.busenamli.moviesapp.ui.uistate

import com.busenamli.moviesapp.data.model.CastModel
import com.busenamli.moviesapp.data.model.MovieDetailModel

data class MovieDetailUiState (
    val isLoading: Boolean? = false,
    val movie: MovieDetailModel? = null,
    val cast: List<CastModel>?= null,
    val isError: Boolean? = false,
    val errorMessage: List<Message>? = listOf(),
    val selectedGenre: Int? = null
)