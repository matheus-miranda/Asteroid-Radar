package com.udacity.asteroidradar.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.core.Constants
import com.udacity.asteroidradar.core.DateUtils
import com.udacity.asteroidradar.domain.usecases.asteroid.CacheNetworkAsteroidsUseCase
import com.udacity.asteroidradar.domain.usecases.picture.CacheNetworkPictureUseCase
import org.koin.java.KoinJavaComponent.inject
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    private val cacheNetworkPictureUseCase: CacheNetworkPictureUseCase by inject(
        CacheNetworkPictureUseCase::class.java
    )
    private val cacheNetworkAsteroidsUseCase: CacheNetworkAsteroidsUseCase by inject(
        CacheNetworkAsteroidsUseCase::class.java
    )
    private val dateUtils by lazy { DateUtils() }

    override suspend fun doWork(): Result {

        return try {
            cacheNetworkAsteroidsUseCase(
                dateUtils.getTodayDate(), dateUtils.getWeekFromNowDate(), Constants.API_KEY
            )
            cacheNetworkPictureUseCase(Constants.API_KEY)
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }
}