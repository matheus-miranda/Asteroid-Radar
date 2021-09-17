package com.udacity.asteroidradar

import android.app.Application
import com.udacity.asteroidradar.data.di.databaseModule
import com.udacity.asteroidradar.data.di.networkModule
import com.udacity.asteroidradar.data.di.repositoryModule
import com.udacity.asteroidradar.domain.di.domainModule
import com.udacity.asteroidradar.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class AsteroidApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AsteroidApp)
            modules(
                networkModule,
                databaseModule,
                repositoryModule,
                domainModule,
                presentationModule
            )
        }

        Timber.plant(Timber.DebugTree())
    }
}