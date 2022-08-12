package com.busenamli.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class MoviesResponseModel(
    @SerializedName("results")
    val movieList: List<MovieModel> = listOf()
)