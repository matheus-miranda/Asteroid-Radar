package com.udacity.asteroidradar.domain.usecases.picture

import com.udacity.asteroidradar.domain.repository.NasaRepository

class DeletePicturesFromDbUseCase(private val repository: NasaRepository) {

    suspend operator fun invoke() {
        repository.deletePicturesFromDatabase()
    }
}