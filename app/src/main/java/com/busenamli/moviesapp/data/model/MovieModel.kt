package com.busenamli.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("adult")
    val adult:Boolean,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("poster_path")
    val posterPath: String
)