package com.imthiyas.moviediscoveryapp.viewmodel

import androidx.lifecycle.ViewModel
import com.imthiyas.moviediscoveryapp.data.request.Movie
import com.imthiyas.moviediscoveryapp.repository.MovieRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    private val _tvShows = MutableStateFlow<List<Movie>>(emptyList())
    val tvShows: StateFlow<List<Movie>> = _tvShows

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    private val disposable = CompositeDisposable()

    init {
        fetchMoviesAndTVShows()
    }

    private fun fetchMoviesAndTVShows() {
        disposable.add(
            repository.getMoviesAndTVShows()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ (moviesResponse, tvShowsResponse) ->
                    _movies.value = moviesResponse.movies
                    _tvShows.value = tvShowsResponse.movies
                    _loading.value = false
                }, {
                    _loading.value = false
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}