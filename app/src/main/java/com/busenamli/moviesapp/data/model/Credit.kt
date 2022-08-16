package com.busenamli.moviesapp.data.model

import com.google.gson.annotations.SerializedName

class Credit(
    @SerializedName("id")
    val id: Int,
    @SerializedName("cast")
    val castModel: List<Cast>,
    @SerializedName("crew")
    val crewModel: List<Crew>
)