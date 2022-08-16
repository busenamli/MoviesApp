package com.busenamli.moviesapp.data.model

import com.google.gson.annotations.SerializedName

class Cast(
    @SerializedName("id")
    val id: Int,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("known_for_department")
    val department: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("cast_id")
    val castId: Int,
    @SerializedName("character")
    val character: String,
    @SerializedName("credit_id")
    val creditId: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("profile_path")
    val profilePath: String
)