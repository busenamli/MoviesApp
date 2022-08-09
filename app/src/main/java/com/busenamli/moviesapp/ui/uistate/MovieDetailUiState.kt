package com.busenamli.moviesapp.ui.uistate

import com.busenamli.moviesapp.data.model.CreditModel
import com.busenamli.moviesapp.data.model.MoviesDetailModel

class MovieDetailUiState (
    val isLoading: Boolean? = false,
    val movie: MoviesDetailModel? = null,
    val credit: CreditModel?= null,
    val error: String? = null
)