package com.udacity.asteroidradar

import android.app.Application
import android.os.Build
import androidx.work.*
import com.udacity.asteroidradar.data.di.databaseModule
import com.udacity.asteroidradar.data.di.networkModule
import com.udacity.asteroidradar.data.di.repositoryModule
import com.udacity.asteroidradar.data.worker.RefreshDataWorker
import com.udacity.asteroidradar.domain.di.domainModule
import com.udacity.asteroidradar.presentation.di.presentationModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import java.util.concurrent.TimeUnit

class AsteroidApp : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

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
        delayedInit()
    }

    /**
     * We create this function to run the initialization in the background so the UI doesn't freeze
     * waiting to be loaded.
     */
    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        // Create a request that runs every day
        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.DAYS
        ).setConstraints(constraints).build()

        // Keep requests when two requests with same work name are enqueued, which will discard the new request
        // REPLACE - replaces old request with new one
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}