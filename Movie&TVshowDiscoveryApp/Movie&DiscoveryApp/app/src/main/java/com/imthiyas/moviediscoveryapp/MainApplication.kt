package com.imthiyas.moviediscoveryapp

import android.app.Application
import com.imthiyas.moviediscoveryapp.di.appModule
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
}