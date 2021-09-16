package com.udacity.asteroidradar.data.remote.api

import com.udacity.asteroidradar.data.remote.dto.PictureOfDay
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {

    @GET("neo/rest/v1/feed?")
    suspend fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): Response<String>

    @GET("planetary/apod?")
    suspend fun getApod(
        @Query("api_key") apiKey: String
    ): Deferred<PictureOfDay>
}