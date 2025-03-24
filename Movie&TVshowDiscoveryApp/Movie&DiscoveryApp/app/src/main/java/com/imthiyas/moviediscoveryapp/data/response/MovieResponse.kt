package com.imthiyas.moviediscoveryapp.data.response

import com.google.gson.annotations.SerializedName
import com.imthiyas.moviediscoveryapp.data.request.Movie

data class MovieResponse(
    @SerializedName("titles") val movies: List<Movie>
)
