package com.udacity.asteroidradar.data.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.udacity.asteroidradar.core.Constants
import com.udacity.asteroidradar.data.database.AppDatabase
import com.udacity.asteroidradar.data.remote.api.NasaApi
import com.udacity.asteroidradar.data.remote.api.ScalarOrMoshiConverter
import com.udacity.asteroidradar.data.repositoryimpl.NasaRepositoryImpl
import com.udacity.asteroidradar.domain.repository.NasaRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import timber.log.Timber

/**
 * We need to make the call depending on which Converter factory will be used
 */
val networkModule = module {

    factory {
        val interceptor = HttpLoggingInterceptor { logger ->
            Timber.e("OkHttp: $logger")
        }
        interceptor.level = HttpLoggingInterceptor.Level.NONE
        interceptor.redactHeader("Authorization")
        interceptor.redactHeader("Cookie")
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    single { createService<NasaApi>(client = get()) }

}

private inline fun <reified T> createService(client: OkHttpClient): T {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(ScalarOrMoshiConverter.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build().create(T::class.java)
}

/**
 * Module for the database. Both DAO interfaces will use this DB.
 */
val databaseModule = module {
    single { AppDatabase.getInstance(context = androidApplication()) }
}

/**
 * Repository module
 */
val repositoryModule = module {
    factory<NasaRepository> { NasaRepositoryImpl(apiService = get(), database = get()) }
}