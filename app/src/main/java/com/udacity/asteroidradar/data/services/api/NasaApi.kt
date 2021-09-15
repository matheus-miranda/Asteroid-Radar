package com.udacity.asteroidradar.data.services.api

import com.udacity.asteroidradar.data.services.dto.PictureOfDay
import kotlinx.coroutines.Deferred
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {

    @GET("neo/rest/v1/feed?")
    fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): Response<JSONObject>

    @GET("planetary/apod?")
    fun getApod(
        @Query("api_key") apiKey: String
    ): Deferred<PictureOfDay>
}