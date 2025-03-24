package com.imthiyas.moviediscoveryapp.data.response

import com.google.gson.annotations.SerializedName
import com.imthiyas.moviediscoveryapp.data.request.TvShow

data class TvShowResponse(
    @SerializedName("titles") val tvShows: List<TvShow>
)
