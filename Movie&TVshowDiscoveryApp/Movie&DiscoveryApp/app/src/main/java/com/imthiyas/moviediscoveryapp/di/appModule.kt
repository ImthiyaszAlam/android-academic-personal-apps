package com.imthiyas.moviediscoveryapp.di


import com.imthiyas.moviediscoveryapp.network.MovieApiService
import com.imthiyas.moviediscoveryapp.repository.MovieRepository
import com.imthiyas.moviediscoveryapp.viewmodel.MovieViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://api.watchmode.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
            .create(MovieApiService::class.java)
    }

    single { MovieRepository(get()) }

    viewModel { MovieViewModel(get()) }
}
