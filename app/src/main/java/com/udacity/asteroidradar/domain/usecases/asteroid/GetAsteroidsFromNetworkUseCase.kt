package com.udacity.asteroidradar.domain.usecases.asteroid

import com.udacity.asteroidradar.domain.model.Asteroid
import com.udacity.asteroidradar.domain.repository.NasaRepository

class GetAsteroidsFromNetworkUseCase(private val repository: NasaRepository) {

    suspend operator fun invoke(
        startDate: String,
        endDate: String,
        apiKey: String
    ): List<Asteroid> {
        return repository.getAsteroidsFromNetwork(startDate, endDate, apiKey)
    }
}