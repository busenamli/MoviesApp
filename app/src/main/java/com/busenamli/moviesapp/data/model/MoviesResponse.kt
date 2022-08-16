package com.busenamli.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("results")
    val movieList: List<Movie> = listOf()
)