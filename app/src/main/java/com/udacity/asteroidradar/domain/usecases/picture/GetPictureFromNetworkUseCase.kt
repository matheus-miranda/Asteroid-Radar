package com.udacity.asteroidradar.domain.usecases.picture

import com.udacity.asteroidradar.domain.model.Picture
import com.udacity.asteroidradar.domain.repository.NasaRepository

class GetPictureFromNetworkUseCase(private val repository: NasaRepository) {

    suspend operator fun invoke(apiKey: String): Picture {
        return repository.getPictureFromNetwork(apiKey)
    }
}