package com.busenamli.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class CountryModel(
    @SerializedName("iso_3166_1")
    val iso31661: String,
    @SerializedName("name")
    val name: String
)