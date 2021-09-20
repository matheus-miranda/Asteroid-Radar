package com.udacity.asteroidradar.domain.usecases.asteroid

import com.udacity.asteroidradar.domain.repository.NasaRepository

class CacheNetworkAsteroidsUseCase(private val repository: NasaRepository) {

    suspend operator fun invoke(
        startDate: String,
        endDate: String,
        apiKey: String
    ) {
        val networkAsteroids = repository.getAsteroidsFromNetwork(startDate, endDate, apiKey)
        repository.saveAsteroidsToDatabase(networkAsteroids.toTypedArray())
    }
}