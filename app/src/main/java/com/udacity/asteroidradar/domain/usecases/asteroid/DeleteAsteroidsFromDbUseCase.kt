package com.udacity.asteroidradar.domain.usecases.asteroid

import com.udacity.asteroidradar.domain.repository.NasaRepository

class DeleteAsteroidsFromDbUseCase(private val repository: NasaRepository) {

    suspend operator fun invoke() {
        repository.deleteAsteroidsFromDatabase()
    }
}