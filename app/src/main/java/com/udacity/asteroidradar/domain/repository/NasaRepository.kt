package com.udacity.asteroidradar.domain.repository

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.domain.model.Asteroid
import com.udacity.asteroidradar.domain.model.Picture
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface NasaRepository {

    /*******************************************************************************
     * Remote
     ******************************************************************************/

    // NeoWs (Near Earth Object Web Service)
    suspend fun getAsteroidsFromNetwork(
        startDate: String,
        endDate: String,
        apiKey: String
    ): Response<String>

    // APOD (Astronomy Picture of the Day)
    suspend fun getPictureFromNetwork(apiKey: String): Deferred<Picture>

    /*******************************************************************************
     * Local
     ******************************************************************************/

    // NeoWs

    fun getAsteroidsFromDatabase(): LiveData<List<Asteroid>>

    suspend fun saveAsteroidsToDatabase(vararg asteroids: Asteroid)

    suspend fun deleteAsteroidsFromDatabase()

    // APOD

    fun getPictureFromDatabase(): LiveData<Picture>

    suspend fun savePictureToDatabase(picture: Picture)

    suspend fun deletePicturesFromDatabase()
}