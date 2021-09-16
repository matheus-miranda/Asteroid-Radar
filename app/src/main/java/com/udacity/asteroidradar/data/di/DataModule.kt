package com.udacity.asteroidradar.data.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.core.Constants.BASE_URL
import com.udacity.asteroidradar.data.services.api.NasaApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * We will have two calls to the API, for the Astronomy Picture of the Day with Moshi, and for the
 * Near Earth Object Web Service, with Scalars.
 */
val networkModule = module {

    factory { ScalarsConverterFactory.create() }
    factory { MoshiConverterFactory.create(moshi) }

    single { createAsteroidService<NasaApi>(factory = get()) }
    single { createApodService<NasaApi>(factory = get()) }

}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private inline fun <reified T> createAsteroidService(factory: ScalarsConverterFactory): T {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(factory)
        .build().create(T::class.java)
}

private inline fun <reified T> createApodService(factory: MoshiConverterFactory): T {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(factory)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build().create(T::class.java)
}