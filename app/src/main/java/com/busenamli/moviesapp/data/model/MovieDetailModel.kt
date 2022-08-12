package com.busenamli.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetailModel(
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
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("genres")
    val genres: List<GenreModel>,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<LanguageModel>,
    @SerializedName("status")
    val status: String,
    @SerializedName("tagline")
    val tagline: String,
    @SerializedName("production_countries")
    val productionCountries: List<CountryModel>,
    @SerializedName("production_companies")
    val productionCompanies: List<CompanyModel>,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("vote_average")
    val voteAverage: Double
)