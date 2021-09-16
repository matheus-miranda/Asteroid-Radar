package com.udacity.asteroidradar

import android.app.Application
import com.udacity.asteroidradar.data.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AsteroidApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AsteroidApp)
            modules(networkModule)
        }
    }
}