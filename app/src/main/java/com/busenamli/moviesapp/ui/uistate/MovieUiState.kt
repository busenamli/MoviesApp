package com.busenamli.moviesapp.ui.uistate

import androidx.paging.PagingData
import com.busenamli.moviesapp.data.model.MovieModel

class MovieUiState (
    val isLoading: Boolean? = false,
    val movieList: PagingData<MovieModel>? = PagingData.empty(),
    val error: String? = null
)