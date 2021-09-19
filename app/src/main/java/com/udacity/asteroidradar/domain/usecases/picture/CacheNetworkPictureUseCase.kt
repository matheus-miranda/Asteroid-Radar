package com.udacity.asteroidradar.domain.usecases.picture

import com.udacity.asteroidradar.domain.repository.NasaRepository

class CacheNetworkPictureUseCase(private val repository: NasaRepository) {

    suspend operator fun invoke(apiKey: String) {
        val picture = repository.getPictureFromNetwork(apiKey)
        if (picture.mediaType == "image") {
            repository.deletePicturesFromDatabase()
            repository.savePictureToDatabase(picture)
        }
    }
}