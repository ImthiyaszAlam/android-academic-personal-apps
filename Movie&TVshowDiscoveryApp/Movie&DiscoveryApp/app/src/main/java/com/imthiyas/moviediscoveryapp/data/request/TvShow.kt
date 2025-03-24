package com.imthiyas.moviediscoveryapp.data.request

import com.google.gson.annotations.SerializedName

data class TvShow(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("year") val year: Int,
    @SerializedName("poster") val poster: String?,
    @SerializedName("overview") val overview: String?
)
