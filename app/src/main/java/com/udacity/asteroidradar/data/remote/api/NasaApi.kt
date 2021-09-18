package com.udacity.asteroidradar.data.remote.api

import com.udacity.asteroidradar.data.remote.dto.PictureOfDay
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {

    @Scalar
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): Response<String>

    @Json
    @GET("planetary/apod")
    suspend fun getApod(
        @Query("api_key") apiKey: String
    ): PictureOfDay
}