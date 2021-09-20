package com.udacity.asteroidradar.domain.repository

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.domain.model.Asteroid
import com.udacity.asteroidradar.domain.model.Picture

interface NasaRepository {

    /*******************************************************************************
     * Remote
     ******************************************************************************/

    // NeoWs (Near Earth Object Web Service)
    suspend fun getAsteroidsFromNetwork(
        startDate: String,
        endDate: String,
        apiKey: String
    ): List<Asteroid>

    // APOD (Astronomy Picture of the Day)
    suspend fun getPictureFromNetwork(apiKey: String): Picture

    /*******************************************************************************
     * Local
     ******************************************************************************/

    // NeoWs

    fun getWeekAsteroidsFromDatabase(today: String): LiveData<List<Asteroid>>

    suspend fun saveAsteroidsToDatabase(asteroidList: Array<Asteroid>)

    suspend fun deleteAsteroidsFromDatabase()

    // APOD

    fun getPictureFromDatabase(): LiveData<Picture>

    suspend fun savePictureToDatabase(picture: Picture)

    suspend fun deletePicturesFromDatabase()
}