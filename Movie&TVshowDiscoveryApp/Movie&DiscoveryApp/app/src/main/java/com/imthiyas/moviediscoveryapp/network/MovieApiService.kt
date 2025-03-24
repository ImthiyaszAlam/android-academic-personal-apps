package com.imthiyas.moviediscoveryapp.network

import com.imthiyas.moviediscoveryapp.data.response.MovieResponse
import com.imthiyas.moviediscoveryapp.data.response.TvShowResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("list-titles/")
    fun getMovies(
        @Query("apiKey") apiKey: String,
        @Query("type") type: String = "movie"
    ): Single<MovieResponse>

    @GET("list-titles/")
    fun getTvShows(
        @Query("apiKey") apiKey: String,
        @Query("type") type: String = "tv_series"
    ): Single<TvShowResponse>
}