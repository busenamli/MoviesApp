package com.busenamli.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class GenresResponseModel(
    @SerializedName("genres")
    val genreList: List<GenreModel> = listOf()
)

data class GenreModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)