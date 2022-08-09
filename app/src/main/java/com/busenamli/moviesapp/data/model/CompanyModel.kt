package com.busenamli.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class CompanyModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo_path")
    val logoPath:String, //https://image.tmdb.org/t/p/original/hUzeosd33nzE5MCNsZxCGEKTXaQ.png
    @SerializedName("name")
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String
)