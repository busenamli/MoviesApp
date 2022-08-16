package com.busenamli.moviesapp.ui.uistate

import com.busenamli.moviesapp.data.model.Cast
import com.busenamli.moviesapp.data.model.MovieDetail

data class MovieDetailUiState(
    val isLoading: Boolean? = false,
    val movie: MovieDetail? = null,
    val cast: List<Cast>? = null,
    val isError: Boolean? = false,
    val errorMessage: List<Message>? = listOf(),
    val selectedGenre: Int? = null
)