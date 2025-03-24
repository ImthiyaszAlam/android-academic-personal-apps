package com.imthiyas.moviediscoveryapp.repository
import com.imthiyas.moviediscoveryapp.data.response.MovieResponse
import com.imthiyas.moviediscoveryapp.network.MovieApiService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.Single.zip

class MovieRepository(private val apiService: MovieApiService) {
    private val API_KEY = "h7vHyMBJIJE7RpfgN0koz6agTS3JgLpX4uH0gBEd"


    fun getMoviesAndTVShows(): Single<Pair<MovieResponse, MovieResponse>> {
        val movies = apiService.getMovies(API_KEY, "movie")
        val tvShows = apiService.getMovies(API_KEY, "tv_series")
        return zip(movies, tvShows) { moviesResponse, tvShowsResponse ->
            Pair(moviesResponse, tvShowsResponse)
        }
    }

}