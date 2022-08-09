package com.busenamli.moviesapp.data.model

import com.google.gson.annotations.SerializedName

class CreditModel (
    @SerializedName("id")
    val id: Int,
    @SerializedName("cast")
    val castModel: List<CastModel>,
    @SerializedName("crew")
    val crewModel: List<CrewModel>
)