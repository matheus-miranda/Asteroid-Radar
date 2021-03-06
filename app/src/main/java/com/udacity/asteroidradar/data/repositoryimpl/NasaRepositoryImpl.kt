package com.udacity.asteroidradar.data.repositoryimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.data.database.AppDatabase
import com.udacity.asteroidradar.data.mappers.asDatabaseModel
import com.udacity.asteroidradar.data.mappers.asDomainObject
import com.udacity.asteroidradar.data.remote.api.NasaApi
import com.udacity.asteroidradar.data.remote.util.parseAsteroidsJsonResult
import com.udacity.asteroidradar.domain.model.Asteroid
import com.udacity.asteroidradar.domain.model.Picture
import com.udacity.asteroidradar.domain.repository.NasaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class NasaRepositoryImpl(
    private val apiService: NasaApi,
    database: AppDatabase,
) : NasaRepository {

    private val asteroidDao = database.asteroidDao
    private val pictureDao = database.pictureDao

    /*******************************************************************************
     * Remote
     ******************************************************************************/

    override suspend fun getAsteroidsFromNetwork(
        startDate: String,
        endDate: String,
        apiKey: String
    ): List<Asteroid> {
        var asteroidList = mutableListOf<Asteroid>()
        try {
            val response = apiService.getAsteroids(startDate, endDate, apiKey)
            if (response.isSuccessful) {
                response.body()?.let {
                    asteroidList = parseAsteroidsJsonResult(JSONObject(it))
                }
            } else {
                Timber.e(response.errorBody().toString())
            }
        } catch (e: HttpException) {
            Timber.e(e.message())
        } catch (e: IOException) {
            Timber.e(e.message)
        }
        return asteroidList
    }

    override suspend fun getPictureFromNetwork(apiKey: String): Picture {
        // Default picture
        var picture = Picture(
            "North America and the Pelican",
            "image",
            "https://apod.nasa.gov/apod/image/2109/NGC7000_SHO_AndrewKlinger_res65_sig1024.jpg",
            "2021-09-17"
        )
        try {
            picture = apiService.getApod(apiKey).asDomainObject()
        } catch (e: HttpException) {
            Timber.e("HttpException: " + e.response()?.errorBody()?.toString())
        } catch (e: IOException) {
            Timber.e("IOException: " + e.message)
        }
        return picture
    }

    /*******************************************************************************
     * Local
     ******************************************************************************/

    override fun getAllAsteroidsFromDatabase(): LiveData<List<Asteroid>> {
        return Transformations.map(asteroidDao.getAllAsteroids()) {
            it.asDomainObject()
        }
    }

    override fun getWeekAsteroidsFromDatabase(today: String): LiveData<List<Asteroid>> {
        val asteroids = Transformations.map(asteroidDao.getWeeklyAsteroids(today)) {
            it.asDomainObject()
        }
        return asteroids
    }

    override suspend fun saveAsteroidsToDatabase(asteroidList: Array<Asteroid>) {
        withContext(Dispatchers.IO) {
            asteroidDao.insertAsteroids(*asteroidList.asDatabaseModel())
        }
    }

    override suspend fun deleteAsteroidsFromDatabase() {
        withContext(Dispatchers.IO) {
            asteroidDao.deleteAllAsteroids()
        }
    }

    override fun getPictureFromDatabase(): LiveData<Picture> {
        return Transformations.map(pictureDao.getPicture()) { databasePicture ->
            databasePicture?.asDomainObject()
        }
    }

    override suspend fun savePictureToDatabase(picture: Picture) {
        withContext(Dispatchers.IO) {
            pictureDao.insertPicture(picture.asDatabaseModel())
        }
    }

    override suspend fun deletePicturesFromDatabase() {
        withContext(Dispatchers.IO) {
            pictureDao.deleteAllPictures()
        }
    }
}