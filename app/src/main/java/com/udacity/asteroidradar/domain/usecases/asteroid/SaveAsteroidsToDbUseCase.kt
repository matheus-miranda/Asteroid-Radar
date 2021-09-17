package com.udacity.asteroidradar.domain.usecases.asteroid

import com.udacity.asteroidradar.domain.model.Asteroid
import com.udacity.asteroidradar.domain.repository.NasaRepository

class SaveAsteroidsToDbUseCase(private val repository: NasaRepository) {

    suspend operator fun invoke(asteroidList: Array<Asteroid>) {
        repository.saveAsteroidsToDatabase(asteroidList)
    }
}