package com.udacity.asteroidradar.data.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.udacity.asteroidradar.core.Constants
import com.udacity.asteroidradar.data.database.AppDatabase
import com.udacity.asteroidradar.data.remote.api.NasaApi
import com.udacity.asteroidradar.data.remote.api.ScalarOrMoshiConverter
import com.udacity.asteroidradar.data.repositoryimpl.NasaRepositoryImpl
import com.udacity.asteroidradar.domain.repository.NasaRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * We need to make the call depending on which Converter factory will be used
 */
val networkModule = module {
    single { createService<NasaApi>() }
}

private inline fun <reified T> createService(): T {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
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